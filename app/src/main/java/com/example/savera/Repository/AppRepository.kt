package com.example.savera.Repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.savera.Model.UserInformation
import com.example.savera.Model.events_Data
import com.example.savera.Model.syllabusshower
import com.example.savera.Model.ChapterList
import com.example.savera.Model.adminDetails
import com.example.savera.Model.feedBackType
import com.example.savera.Model.nameAndAtt
import com.example.savera.Model.studentAttendanceData
import com.example.savera.Model.topicList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

object AppRepository {


    // Function to fetch a list of documents within a collection
    fun fetchDocuments(
        collectionName: String,
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        val collectionRef = firestore.collection(collectionName)
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                val documentList = mutableListOf<String>()
                for (document in querySnapshot.documents) {
                    documentList.add(document.id)
                }
                onSuccess(documentList)

            }
            .addOnFailureListener { exception ->
                onFailure(exception)

            }
    }

    // Function to fetch a list of subcollections within a document
    fun GetStudentList(
        documentPath: String, collectionName: String,
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        firestore
            .collection(collectionName)
            .document(documentPath)
            .collection("Students")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documentList = mutableListOf<String>()
                for (document in documentSnapshot.documents) {
                    documentList.add(document.id)
                }

                onSuccess(documentList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // function to add the data to attendance
    fun adddata(
        collectionName: String, documentPath: String,
        studentName: String, date: String,
        data: Any, error: (String) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(collectionName)
            .document(documentPath)
            .collection("Students")
            .document(studentName)
            .collection("Attendance")
            .document(date)
            .set(data)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                error(it.localizedMessage)
            }
    }

    // function to add the feed back
    // also add  present Student
    fun addfeedback(
        collectionName: String, documentPath: String,
        hashMap: HashMap<String, String>,
        successfull: () -> Unit,
        failure: (String) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(collectionName).get().addOnSuccessListener { documentSnapshot ->
            val documentList = mutableListOf<String>()
            for (document in documentSnapshot.documents) {
                documentList.add(document.id)
            }

            if (documentList.contains(documentPath))
                firestore.collection(collectionName)
                    .document(documentPath)
                    .update(hashMap as Map<String, Any>)
                    .addOnSuccessListener {
                        successfull()
                    }
                    .addOnFailureListener {
                        failure(it.localizedMessage.toString())
                    }
            else
                firestore.collection(collectionName)
                    .document(documentPath)
                    .set(hashMap as Map<String, Any>)
                    .addOnSuccessListener {
                        successfull()
                    }
                    .addOnFailureListener {
                        failure(it.localizedMessage.toString())


                    }


        }


    }


// add events details

    fun addevents(
        collectionName: String,
        documentPath: String,
        list: HashMap<String, String>,
        successfull: () -> Unit,
        failure: (String) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(collectionName)
            .document(documentPath)
            .set(list)
            .addOnSuccessListener {
                successfull()
            }
            .addOnFailureListener {
                failure(it.localizedMessage)
            }
    }

    // getting events from firebase
    fun getEvents(): MutableStateFlow<List<events_Data>> {
        var eventsState: MutableStateFlow<List<events_Data>> = MutableStateFlow(emptyList())

        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("Events")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // Handle any exceptions
                    return@addSnapshotListener
                }

                val events = mutableListOf<events_Data>()

                snapshot?.documents?.forEach { documents ->
                    val event = events_Data(
                        document = documents.id,
                        date = documents.getString("Date") ?: "",
                        description = documents.getString("Description") ?: "",
                        eventName = documents.getString("Event Name") ?: "",
                        location = documents.getString("Location") ?: "",
                        time = documents.getString("Time") ?: "",
                        createdBy = documents.getString("created by") ?: "",
                        imageUrl = documents.getString("imageurl") ?: ""
                    )

                    events.add(event)
                }

                eventsState.value = events
            }


        return eventsState
    }

    suspend fun checkYearInformation(
        currentUser: String,
        informationExists: () -> Unit,
        informationMissing: () -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        if (currentUser != null) {
            val userDocRef = firestore.collection("teachers")
                .document(currentUser)
            val documentSnapshot = userDocRef.get().await()

            if (documentSnapshot.exists()) {
                // Check for the "name" field specifically
                val name = documentSnapshot.getString("Name")
                if (name != null) {
                    informationExists()
                } else {
                    informationMissing()
                }
            } else {
                informationMissing()  // Document doesn't exist at all
            }
        }
    }



    // new user add code
    fun addnewuser(
        documentPath: String, newData: Map<String, Any>, successfull: () -> Unit,
        failure: (String) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("teachers")
            .document(documentPath)
            .update(newData)
            .addOnSuccessListener {
                successfull()
            }
            .addOnFailureListener {
                failure(it.localizedMessage)
            }
        subscribeToGroups()


    }
    fun subscribeToGroups(){
        Log.d("lakshay", "subscribeToGroups: Its working")
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val userEmail = user.email
            if (userEmail != null) {
                db.collection("teachers")
                    .document(userEmail)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val year = document.getString("Year")
                            year?.let { userYear ->
                                subscribeToFCMTopics(userYear)
                                Log.d("lakshay", "subscribeToGroups: Its working2 $userYear")

                            }
                        } else {
                            Log.d("lakshay", "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("lakshay", "get failed with ", exception)
                    }
            }
        }

    }
    private fun subscribeToFCMTopics(year: String) {
        when (year.toIntOrNull()) {
            1 -> {
                subscribeToTopic("1st_Year")
                subscribeToTopic("1st_2nd_Year")
                subscribeToTopic("official")

            }
            2 -> {
                subscribeToTopic("2nd_Year")
                subscribeToTopic("1st_2nd_Year")
                subscribeToTopic("2nd_3rd_Year")
                subscribeToTopic("official")
            }
            3 -> {
                subscribeToTopic("3rd_Year")
                subscribeToTopic("2nd_3rd_Year")
                subscribeToTopic("official")
                subscribeToTopic("3rd_4th_Year")

            }
            4 -> {
                subscribeToTopic("4th_Year")
                subscribeToTopic("3rd_4th_Year")
                subscribeToTopic("official")

            }
            else -> {
            }
        }
    }
    private fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("lakshay", "Subscribed to $topic topic")
                } else {
                    Log.e("lakshay", "Failed to subscribe to $topic topic", task.exception)
                }
            }
    }




    // Add new Student
    fun addNewStudent(
        name: String, className: String, data: Any, successfull: () -> Unit,
        error: (String) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("Classes")
            .document(className)
            .collection("Students")
            .document(name)
            .set(data)
            .addOnSuccessListener {
                successfull()
            }
            .addOnFailureListener {
                error(it.localizedMessage)
            }
    }

    //check if attendance taken or not
    suspend fun checkAttendanceTakenOrNot(
        className: String,
        date: String,
        taken: () -> Unit,
        nottaken: () -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        Log.d("wfbkjfcewfllewmfkjrhfwefklhwjklqwewr", date)
        val userDocRef = firestore.collection("Classes")
            .document(className)
        val documentSnapshot = userDocRef.get().await()
        if (documentSnapshot.contains(date)) {
            taken()
        } else {
            nottaken()
        }

    }
//  user information take

    fun userInformat(
        email: String,
        onSuccess: (UserInformation) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()


        firestore.collection("teachers")
            .document(email)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val data = UserInformation(
                        attendance = document.getString("Attendance") ?: "",
                        name = document.getString("Name") ?: "",
                        phone = document.getString("Phone") ?: "",
                        profilePic = document.getString("ProfilePic") ?: "",
                        year = document.getString("Year") ?: "",
                        gender = document.getString("Gender") ?: "",
                        admin = document.getString("Admin")?:"False"
                    )
                    onSuccess(data)
                }
            }
            .addOnFailureListener {
                it.localizedMessage?.let { it1 -> onFailure(it1) }
            }

    }

    fun updadeteachersData(
        documentPath: String, successfull: () -> Unit,
        failure: (String) -> Unit,
        data: HashMap<String, Any>,
    ) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("teachers")
            .document(documentPath)
            .update(data)
            .addOnSuccessListener {
                successfull()
            }
            .addOnFailureListener {
                failure(it.localizedMessage)
            }

    }


    // deleting document
    fun deleteEvent(
        documentPath: String, fileUrl: String,
        success: () -> Unit,
        failure: (String) -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        val storageref = Firebase.storage.getReferenceFromUrl(fileUrl)

        storageref.delete().addOnSuccessListener {
            firestore.collection("Events")
                .document(documentPath).delete().addOnSuccessListener {
                    success()
                }
                .addOnFailureListener {
                    failure(it.localizedMessage)
                }
        }
    }
// fetching syllabus

    @SuppressLint("SuspiciousIndentation")
    fun fetchSyllabus(
        className: String,
        successfull: (List<syllabusshower>) -> Unit,
    ) {

        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("syllabus")
            .document(className)
            .collection("Syllabus")
            .get()
            .addOnSuccessListener {
                val data = mutableListOf<syllabusshower>()

                for (subjects in it) {


                    val previous = subjects.getString("previous")
                    val status = subjects.getBoolean("completed")
                    val percentage = subjects.getLong("percentage")
                    data.add(
                        syllabusshower(
                            syllabus = subjects.id,
                            previous = previous,
                            status = status ?: false,
                            percentage = percentage
                        )
                    )
                }

                successfull(data)


            }


    }

    // fetch the Chapters
    fun fetchChapters(
        classes: String, successfull: (List<ChapterList>) -> Unit,
        subject: String,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("syllabus")
            .document(classes)
            .collection("Syllabus")
            .document(subject)
            .collection("Chapters")
            .get()
            .addOnSuccessListener {
                val document = mutableListOf<ChapterList>()
                var x = 0
                for (d in it) {
                    x++
                    val status = d.getBoolean("status")

                    document.add(
                        ChapterList(
                            name = d.id,
                            status = status ?: false,
                            no = x
                        )
                    )
                }


                successfull(document)

            }
    }


    // fetching the topics
fun fetchTopics(classes: String,
                subject: String,
                chapter:String,
                successfull: (List<topicList>) -> Unit){

val firestore = FirebaseFirestore.getInstance()

        firestore.collection("syllabus")
            .document(classes)
            .collection("Syllabus")
            .document(subject)
            .collection("Chapters")
            .document(chapter)
            .collection("Topics")
            .get()
            .addOnSuccessListener {
                val list = mutableListOf<topicList>()

                for (document in it){
                    val status = document.getBoolean("done")
                    list.add(
                        topicList(
                            name = document.id,
                            status = status?:true
                        )
                    )

                }

                successfull(list)
            }



}


// changing Status
fun ChangeStatus(
    classes: String,
    subject: String,
    chapter:String,
    topic:String,
    data:Any,
){
    val firestore = FirebaseFirestore.getInstance()

    firestore.collection("syllabus")
        .document(classes)
        .collection("Syllabus")
        .document(subject)
        .collection("Chapters")
        .document(chapter)
        .collection("Topics")
        .document(topic)
        .set(
           data
        )
        .addOnSuccessListener {
            firestore.collection("syllabus")
                .document(classes)
                .collection("Syllabus")
                .document(subject)
                .update(
                    "previous" , topic
                )



        }


}


    // volunteers Attendance
    fun volunteersAttendance(
        date: String,
        email: String,
        successfull: () -> Unit
    ){
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("AttendanceVolunteers")
            .document(date)
            .collection("Attendance")
            .document(email).
                set(hashMapOf(
                    "present" to "yes"
                ))
            .addOnSuccessListener {
               firestore.collection("teachers")
                   .document(email)
                   .get()
                   .addOnSuccessListener {

                  var x =     it.getString("Attendance")?.toInt()?:0
                       x += 1

                       firestore.collection("teachers")
                           .document(email)
                           .update("Attendance" , x.toString())
                           .addOnSuccessListener {
                               successfull()
                           }
                   }



            }
    }


    // checking attendance done or not
    fun checking(date:String,email: String,taken: (Boolean) -> Unit){
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("AttendanceVolunteers")
            .document(date)
            .collection("Attendance")
            .document(email).get().addOnSuccessListener {
                if (it.exists()){
                    taken(true)
                }

                else{
                    taken(false)
                }
            }

    }

// calculateTop3
@SuppressLint("SuspiciousIndentation")
fun calculateTop3(list:(List<nameAndAtt>)->Unit){
    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("teachers")
        .get()
        .addOnSuccessListener {
            val data = mutableListOf<nameAndAtt>()
            for (document in it){

              val attendan   =  document.getString("Attendance")?:"0"

                data.add(
                    nameAndAtt(
                        name = document.getString("Name")?:"",
                        attendance = attendan.toInt()
                    )
                )

            }

            list(data)

        }
    }


// getting Volunteers Details
fun getAdminDetails(successfull: (List<adminDetails>) -> Unit){

    val firestore = FirebaseFirestore.getInstance()
firestore.collection("teachers")
    .get()
    .addOnSuccessListener {
        val list = mutableListOf<adminDetails>()

        for (document in it){

            val image = document.getString("ProfilePic")?:""
            val Name = document.getString("Name")?:""
            val admin = document.getString("Admin")?:"False"

            list.add(
                adminDetails(
                    name = Name,
                    admin = admin,
                    image = image,
                    email = document.id
                )
            )

        }

        successfull(list)
    }

}


    fun addAdmin(email: String,
                 value:String){
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("teachers")
            .document(email)
            .update("Admin",value)

    }

// show attendance
    fun showAttendance(date: String,successfull: (List<studentAttendanceData>) -> Unit){
        val firestore = FirebaseFirestore.getInstance()
    firestore.collection("Classes")
        .get()
        .addOnSuccessListener {

            val list = mutableListOf<studentAttendanceData>()

            for (document in it){

                val check =document.contains(date)
                if (
                    check
                ){
                    val field = document.getString(date)?:""
                    list.add(
                        studentAttendanceData(
                            className = document.id,
                            value = field,
                            status = true
                        )
                    )

                }
                else{
                    list.add(
                        studentAttendanceData(
                        className = document.id,
                        value = "",
                        status = false
                    )
                    )
                }


            }

            successfull(list)

        }


    }


    fun fetchSylabus(className: String,successfull: (List<String>) -> Unit){
val firestore= FirebaseFirestore.getInstance()
        firestore.collection("syllabus")
            .document(className)
            .collection("Syllabus")
            .addSnapshotListener { value, error ->
val list = mutableListOf<String>()

                value?.documents?.forEach {
                    list.add(it.id)
                }
                successfull(list)

            }



    }

    // add subjects
    fun addSubjects(className: String,
                    subject: String,
                    successfull: () -> Unit
                    ){

        val firestore= FirebaseFirestore.getInstance()
        firestore.collection("syllabus")
            .document(className)
            .collection("Syllabus")
            .document(subject)
            .set(
                hashMapOf(
                    "completed" to false,
                    "previous" to ""
                )

            )
            .addOnSuccessListener {
                successfull()
            }



    }

fun fetchCHapters(className: String,
                  subject: String,
                  successfull: (List<String>) -> Unit
                  ){
    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("syllabus")
        .document(className)
        .collection("Syllabus")
        .document(subject)
        .collection("Chapters")
        .addSnapshotListener { value, error ->

            val list = mutableListOf<String>()

            value?.documents?.forEach {
                list.add(it.id)
            }
            successfull(list)

        }



}

    fun addChapters(chapter: String,
                    className: String,
                    subject: String,
                    successfull: () -> Unit
                    ){

        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("syllabus")
            .document(className)
            .collection("Syllabus")
            .document(subject)
            .collection("Chapters")
            .document(chapter)
            .set(
                hashMapOf(
                    "status" to false
                )
            )
            .addOnSuccessListener {
                successfull()
            }
    }


    fun fetchTopicsforedit(className: String,
                    subject: String,
                    chapter: String,
                    successfull: (List<String>) -> Unit){

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("syllabus")
            .document(className)
            .collection("Syllabus")
            .document(subject)
            .collection("Chapters")
            .document(chapter)
            .collection("Topics")
            .addSnapshotListener { value, error ->
                val list = mutableListOf<String>()

                value?.documents?.forEach {
                    list.add(it.id)
                }
                successfull(list)


            }


    }

fun addDataToChapters(
    className: String,
    subject: String,
    chapter: String,
    topic: String,
    successfull: () -> Unit

){
    val firestore = FirebaseFirestore.getInstance()

    firestore.collection("syllabus")
        .document(className)
        .collection("Syllabus")
        .document(subject)
        .collection("Chapters")
        .document(chapter)
        .collection("Topics")
        .document(topic)
        .set(
            hashMapOf(
                "done" to false
            )
        ).addOnSuccessListener {
            successfull()
        }
}


    fun seeFeedBack(successfull: (List<feedBackType>) -> Unit){
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("Feedback")
            .get()
            .addOnSuccessListener {
val data = mutableListOf<feedBackType>()

                for (document in it){
                    for ((key,value) in document.data){

                        data.add(
                            feedBackType(
                                email = document.id,
                                date = key,
                                data =  value.toString()
                            )

                        )

                    }

                }
                successfull(data)



            }


    }


}



