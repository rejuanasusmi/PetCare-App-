package com.example.trysomething;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    ArrayList<PostDetails> shopPostDetailsArrayList;
    private SelectListner listner;

    public MyAdapter(ArrayList<PostDetails> shopPostDetailsArrayList,SelectListner listner) {
        this.shopPostDetailsArrayList = shopPostDetailsArrayList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_information,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       /* ShopPostDetails shopPostDetails = shopPostDetailsArrayList.get(position);

        holder.petType.setText(shopPostDetails.PetType);
        holder.petAge.setText(shopPostDetails.PetAge);
        holder.price.setText(shopPostDetails.Price);
        holder.contact.setText(shopPostDetails.Contact);*/
        //Glide.with(context).load(shopPostDetailsArrayList.get(position)) .into(holder.PetImage);


        holder.petType.setText(shopPostDetailsArrayList.get(position).getPetType());
        holder.petAge.setText(shopPostDetailsArrayList.get(position).getPetAge());
        holder.price.setText(shopPostDetailsArrayList.get(position).getPrice());
        holder.contact.setText(shopPostDetailsArrayList.get(position).getContact());
        holder.email.setText(shopPostDetailsArrayList.get(position).getEmail());
        // holder.PetImage.(shopPostDetailsArrayList.get(position).getPetImage());

        String petImageUrl = shopPostDetailsArrayList.get(position).getPetImage();
        if (petImageUrl != null && !petImageUrl.isEmpty()) {
            // Convert content URI to a valid URL string format
            String imageUrl = Uri.parse(petImageUrl).toString();

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.petimagepost1) // Placeholder image
                    .error(R.drawable.facebook); // Error image if loading fails

            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .apply(requestOptions)
                    .into(holder.PetImage);
        }


    }

    @Override
    public int getItemCount() {
        return shopPostDetailsArrayList.size();
    }

    public void addData(PostDetails data) {
        shopPostDetailsArrayList.add(data);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView petType,petAge,price,contact,email;
        ImageView PetImage;
        Button details,buy;

        String i;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            PetImage = (itemView).findViewById(R.id.imagecard);
            petType = (itemView).findViewById(R.id.pettypecard);
            petAge = (itemView).findViewById(R.id.petagecard);
            price = (itemView).findViewById(R.id.petpricecard);
            contact = (itemView).findViewById(R.id.contactcard);
            details = (itemView).findViewById(R.id.detailscardbtn);
            email = (itemView).findViewById(R.id.emailcard);
            buy = (itemView).findViewById(R.id.buycardbtn);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null){
                        int pos = getAdapterPosition();


                        if(pos != RecyclerView.NO_POSITION){
                            listner.onItemClicked(pos);
                        }
                    }
                }
            });

        }
    }

    private boolean isValidContentUri(Uri contentUri, Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            ParcelFileDescriptor descriptor = contentResolver.openFileDescriptor(contentUri, "r");
            if (descriptor != null) {
                descriptor.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
