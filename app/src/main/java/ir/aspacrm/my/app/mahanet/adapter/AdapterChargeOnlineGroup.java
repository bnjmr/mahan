package ir.aspacrm.my.app.mahanet.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.R;
import ir.aspacrm.my.app.mahanet.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedChargeOnlineGroup;
import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineGroup;

public class AdapterChargeOnlineGroup extends RecyclerView.Adapter<AdapterChargeOnlineGroup.GroupViewHolder> {

    List<ChargeOnlineGroup> groups;
    int isClub;
    int whichMenuItem;

    public AdapterChargeOnlineGroup(List<ChargeOnlineGroup> groups, int isClub, int whichMenuItem) {
        this.groups = groups;
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_charge_online_group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        final ChargeOnlineGroup group = groups.get(position);
        holder.txtGroupName.setText("" + group.des);
        if (group.now){
            holder.img_current_service.setVisibility(View.VISIBLE);
        }else {
            holder.img_current_service.setVisibility(View.GONE);

        }
//        holder.layMainGroup.setCardBackgroundColor(Color.parseColor(group.color));
        holder.layMainGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                EventBus.getDefault().post(new EventOnChargeOnlineGroupItemClicked());
                EventBus.getDefault().post(new EventOnClickedChargeOnlineGroup(group.code, isClub, whichMenuItem));
//                Intent intent= new Intent(G.context, ActivityChargeOnlineCategory.class);
//                intent.putExtra("WHICH_MENU_ITEM",whichMenuItem);
//                intent.putExtra("IS_CLUB",isClub);
//                intent.putExtra("GROUP_CODE",group.code);
//                intent.putExtra("GROUP_CODEe",55);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                G.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtGroupName)
        PersianTextViewThin txtGroupName;
        @Bind(R.id.layMainGroup)
        CardView layMainGroup;
        @Bind(R.id.img_current_service)
        ImageView img_current_service;

        public GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ChargeOnlineGroup> data) {
        groups = data;
        notifyDataSetChanged();
    }
}
