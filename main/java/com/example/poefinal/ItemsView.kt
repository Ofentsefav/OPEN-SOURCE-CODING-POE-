package com.example.poefinal

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poefinal.databinding.ActivityItemsViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.ByteArrayOutputStream

class ItemsView : AppCompatActivity() {
    private lateinit var binding: ActivityItemsViewBinding
    private lateinit var categoryName : String

    private lateinit var database: DatabaseReference
    private var our_request_code = 123
    var sImage: String? =""

    private lateinit var itemArrayList: ArrayList<itemDbm>

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var  itemAdapter: itemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name").toString()
        val cateName: TextView = findViewById(R.id.textView4)
        categoryName = name

        binding.viewItems.layoutManager = LinearLayoutManager(this)
        binding.viewItems.hasFixedSize()
        itemArrayList = arrayListOf<itemDbm>()
        //viewItem()
        viewItems()
        cateName.text = name
        binding.InsertItems.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.add_items, null)
            builder.setView(view)
            val itemName:EditText = view.findViewById(R.id.editName)
            val itemDescription: EditText = view.findViewById(R.id.editDescription)
            val itemPrice: EditText = view.findViewById(R.id.editPrice)
            val itemPic: ImageView = view.findViewById(R.id.imageTake)
            val btnSelectPic: Button = view.findViewById(R.id.btnPickImage)
            btnSelectPic.setOnClickListener {
                insert_Image()
            }
            val dialog = builder.create()
            view.findViewById<Button>(R.id.btnReset).setOnClickListener {
               InsertIterms(itemName, itemDescription, itemPrice)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window != null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
        }

    }
    private fun viewItem() {
        itemArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Items")
        ref.orderByChild("categoryName").equalTo(categoryName )
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    itemArrayList.clear()
                    for (ds in snapshot.children){
                        val items = ds.getValue(itemDbm::class.java)
                        if (items != null) {
                            itemArrayList.add(items)
                            Log.d(TAG,"onDataChange:${items.itemName} " )
                        }
                    }

                    binding.viewItems.adapter = itemAdapter(itemArrayList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun viewItems() {
        database = FirebaseDatabase.getInstance().getReference("Items")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (catsnapshot in snapshot.children){
                        val category = catsnapshot.getValue(itemDbm::class.java)
                        itemArrayList.add(category!!)
                    }
                    binding.viewItems.adapter = itemAdapter(itemArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun InsertIterms(itemName: EditText, itemDescription: EditText, itemPrice: EditText) {
        val itemNames = itemName.text.toString()
        val itemDescriptions = itemDescription.text.toString()
        val itemPrices = itemPrice.text.toString()
        val names = intent.getStringExtra("name").toString()
        database = FirebaseDatabase.getInstance().getReference("Items")
        val catagories = itemDbm(itemNames, itemDescriptions, itemPrices, names, sImage.toString())
        val databaseReference = FirebaseDatabase.getInstance().reference
        val id = databaseReference.push().key
        database.child(id.toString()).setValue(catagories).addOnCompleteListener{

            Toast.makeText(this, "Category has been created successfully!!!!!!", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener{
            Toast.makeText(this, "Fail to create category", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun insert_Image(){
        var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.setType("image/*")
        ActivityResultLauncher.launch(myfileintent)


    }
    private val ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult(),
    ){
            result: ActivityResult ->
        if (result.resultCode == RESULT_OK){
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val bytes = stream.toByteArray()
                sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
               // binding.pic.setImageBitmap(myBitmap)
                inputStream!!.close()
                Toast.makeText(this, "Image selected successfully!!", Toast.LENGTH_SHORT)
                    .show()
            }catch (ex: Exception){
                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
