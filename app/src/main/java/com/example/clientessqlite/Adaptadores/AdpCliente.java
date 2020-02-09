package com.example.clientessqlite.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientessqlite.Entidades.Cliente;
import com.example.clientessqlite.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

//CircleImageView civ = (CircleImageView) findViewById(R.id.imagen_circular);
public class AdpCliente extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Cliente> clientes;

    public AdpCliente(Context context, int layout, List<Cliente> clientes) {
        this.context = context;
        this.layout = layout;
        this.clientes = clientes;
    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(this.context);
            convertView=inflater.inflate(this.layout,null);
            holder = new ViewHolder();
            holder.idView=(TextView) convertView.findViewById(R.id.lblIdCliente);
            holder.nombreView=(TextView) convertView.findViewById(R.id.lblNombreCliente);
            holder.Rfcview=(TextView) convertView.findViewById(R.id.lblRfcCliente);
            holder.Correoview=(TextView) convertView.findViewById(R.id.lblCorreoCliente);
            holder.Phoneview=(TextView) convertView.findViewById(R.id.lblTelefonoCliente);
            holder.Direcionview=(TextView) convertView.findViewById(R.id.lblDirreccionCliente);
            holder.Latview=(TextView) convertView.findViewById(R.id.lblLatCliente);
            holder.longview=(TextView) convertView.findViewById(R.id.lblLongCliente);
            holder.foto=(ImageView) convertView.findViewById(R.id.imgfotoCliente);
            convertView.setTag(holder);
        }else{
            holder =(ViewHolder) convertView.getTag();
        }
        Cliente currenCliente = clientes.get(position);
        holder.idView.setText(currenCliente.getIdCliente());
        holder.nombreView.setText(currenCliente.getNombre());
        holder.Rfcview.setText(currenCliente.getRfc());
        holder.Correoview.setText(currenCliente.getCorreo());
        holder.Phoneview.setText(currenCliente.getTelefono());
        holder.Direcionview.setText(currenCliente.getDireccion());
        holder.Latview.setText(currenCliente.getLatitud());
        holder.longview.setText(currenCliente.getLongitud());
        holder.foto.setImageBitmap(bitmap(currenCliente.getImagen()));
        return convertView;
    }
    public Bitmap bitmap(String base64String){
        String base64Image = base64String.split(",")[1];

        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;
    }


    public static class ViewHolder {
        private TextView idView;
        private ImageView foto;
        private CircleImageView circleImageView;
        private TextView nombreView;
        private TextView Rfcview;
        private TextView Correoview;
        private TextView Phoneview;
        private TextView Direcionview;
        private TextView Latview;
        private TextView longview;
}

}
