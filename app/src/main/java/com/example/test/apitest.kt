package com.example.test

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.API.News
import com.example.test.API.RetrofitConnection
import com.example.test.databinding.ActivityApitestBinding
import com.example.test.databinding.ListItemBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.Manifest
import android.app.Notification.Action
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.activity.result.contract.ActivityResultContracts
import java.io.IOException

class apitest : AppCompatActivity() {

    private var _binding : ActivityApitestBinding? = null;

    val binding : ActivityApitestBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityApitestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        RetrofitConnection.getRetrofit().GetNews().enqueue(object : Callback<MutableList<News>> {
            override fun onResponse(
                call: Call<MutableList<News>>,
                response: Response<MutableList<News>>
            ) {
                if(response.code() == 200)
                {
                    setRecyclerView(response.body()!!)
                }
            }

            override fun onFailure(call: Call<MutableList<News>>, t: Throwable) {
                Log.println(Log.ERROR, "API", "Не удалось установить связь");
            }
        })

        binding.MainPhoto.setOnClickListener(object : View.OnClickListener{

            override fun onClick(v: View?) {

                if (ContextCompat.checkSelfPermission(this@apitest, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this@apitest,arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 101)
                }
                else
                {
                    var intent_photo = Intent(Intent.ACTION_PICK);
                    intent_photo.type = "image/*"
//startActivityForResult(intent_photo, 1);
                    changeItem.launch(intent_photo)
                }
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var bitmap: Bitmap? = null

        when (requestCode) {
            1 -> if (resultCode === RESULT_OK) {
                val selectedImage = data?.data
                try {
                    bitmap = getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                binding.MainPhoto.setImageBitmap(bitmap)
            }
        }
    }

    private val changeItem = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == PackageManager.PERMISSION_DENIED) {
            val data = it.data
            val imgUri = data?.data
            binding.MainPhoto.setImageURI(imgUri)
        }
    }

    fun onMyButtonClick(view: View?) {

        val intent = Intent (this@apitest, one::class.java)
        startActivity(intent)
    }

    fun setRecyclerView(itemCollection : MutableList<News>) {
        binding.MainRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.MainRecyclerView.adapter = MainRecyclerViewAdapter(itemCollection);
    }
}

class MainRecyclerViewAdapter(var NewsCollection : MutableList<News>) : RecyclerView.Adapter<MainRecyclerViewViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = ListItemBinding.inflate(layoutInflater)
        return MainRecyclerViewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return NewsCollection.size;
    }

    override fun onBindViewHolder(holder: MainRecyclerViewViewHolder, position: Int) {
        holder.binding.IdNews.text = NewsCollection[position].id.toString();
        holder.binding.DescriptionNews.text = NewsCollection[position].description;
        holder.binding.NameNews.text = NewsCollection[position].name;
        holder.binding.PriceNews.text = NewsCollection[position].price.toString();
        Picasso.get().load(NewsCollection[position].image).into(holder.binding.PictureNews)
    }

}

class MainRecyclerViewViewHolder(var binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root)
