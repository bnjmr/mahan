package ir.aspacrm.my.app.mahanet.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.R;
import ir.aspacrm.my.app.mahanet.model.TicketDetail;

import static ir.aspacrm.my.app.mahanet.G.context;

public class AdapterTicketDetails extends RecyclerView.Adapter<AdapterTicketDetails.TicketViewHolder> {

    final int SEND_MESSAGE = 1;
    final int RECEIVE_MESSAGE = 2;
    List<TicketDetail> tickets;
    String image;

    public AdapterTicketDetails(List<TicketDetail> tickets) {
        this.tickets = tickets;
        image = G.currentUserInfo.Encoded64ImageString;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == SEND_MESSAGE)
            view = LayoutInflater.from(context).inflate(R.layout.l_ticket_detail_right_item, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.l_ticket_detail_left_item, parent, false);

        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        final TicketDetail ticket = tickets.get(position);
        holder.txtTicketDescription.setText(Html.fromHtml(ticket.Des));
        holder.txtTicketDate.setText(ticket.Date);
        holder.txtTicketState.setText(ticket.State);

        if (getItemViewType(position) == SEND_MESSAGE) {
            if (image != null
                    && !image.equals("")
                    && !image.equals("null")) {
                showEncodedImage(image, holder.imgProfile);
            }else {
                holder.imgProfile.setImageResource(R.drawable.user);
            }
        } else {
            holder.imgProfile.setImageResource(R.mipmap.ic_launcher);
        }


//        if (G.currentUserInfo.Encoded64ImageString != null
//                && !G.currentUserInfo.Encoded64ImageString.equals("")
//                && !G.currentUserInfo.Encoded64ImageString.equals("null")) {
//            showEncodedImage(G.currentUserInfo.Encoded64ImageString);
//
//
//            if (!encodeString.equals("") && !encodeString.equals("NotFound") && !encodeString.equals("null")) {
//                G.hasImage = true;
//                imgProfileBtn.setImageResource(R.drawable.ic_edit_image);
//            } else {
//                imgProfileBtn.setImageResource(R.drawable.ic_plus);
//                imgAvatar.setImageResource(R.drawable.user);
//            }
//            final byte[] decodedBytes = Base64.decode(encodeString, Base64.DEFAULT);
////        Glide.with(G.context).load(decodedBytes).crossFade().fitCenter().into(imgAvatar);
//
//            Glide.with(context).load(decodedBytes).into(new SimpleTarget<GlideDrawable>() {
//                @Override
//                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                    imgAvatar.setImageDrawable(resource);
//                }
//            });
//        }
    }

    public void showEncodedImage(String encodeString, final CircularImageView imageView) {
        if (!encodeString.equals("") && !encodeString.equals("NotFound") && !encodeString.equals("null")) {
        } else {
            imageView.setImageResource(R.drawable.user);
        }
        final byte[] decodedBytes = Base64.decode(encodeString, Base64.DEFAULT);
//        Glide.with(G.context).load(decodedBytes).crossFade().fitCenter().into(imgAvatar);

        Glide.with(context).load(decodedBytes).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setImageDrawable(resource);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        final TicketDetail ticket = tickets.get(position);
        if (ticket.User.equals("مشترک"))
            return SEND_MESSAGE;
        else
            return RECEIVE_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtTicketDescription)
        TextView txtTicketDescription;
        @Bind(R.id.txtTicketDate)
        TextView txtTicketDate;
        @Bind(R.id.txtTicketState)
        TextView txtTicketState;
        @Bind(R.id.imgProfile)
        CircularImageView imgProfile;

        public TicketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<TicketDetail> data) {
        tickets = data;
        notifyDataSetChanged();
    }
}
