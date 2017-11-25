package ir.aspacrm.my.app.mahanet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import ir.aspacrm.my.app.mahanet.ActivityChangePassword;
import ir.aspacrm.my.app.mahanet.ActivityChargeOnline;
import ir.aspacrm.my.app.mahanet.ActivityPayments;
import ir.aspacrm.my.app.mahanet.ActivityPoints;
import ir.aspacrm.my.app.mahanet.ActivityShowClubScores;
import ir.aspacrm.my.app.mahanet.ActivityShowConnections;
import ir.aspacrm.my.app.mahanet.ActivityShowFactors;
import ir.aspacrm.my.app.mahanet.ActivityShowFeshfeshe;
import ir.aspacrm.my.app.mahanet.ActivityShowNews;
import ir.aspacrm.my.app.mahanet.ActivityShowNotify;
import ir.aspacrm.my.app.mahanet.ActivityShowTickets;
import ir.aspacrm.my.app.mahanet.ActivityShowTrafficeSplite;
import ir.aspacrm.my.app.mahanet.ActivityShowUserInfo;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.R;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.U;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.model.ModelDrawerItems;

//import ir.aspacrm.my.app.mahanet.ActivityPoints;

/**
 * Created by HaMiD on 1/22/2017.
 */

public class AdapterDrawerRecycler extends RecyclerView.Adapter<AdapterDrawerRecycler.DrawerRecyclerViewHolder> {

    List<ModelDrawerItems> list;
    Context context;
    String link = "";
    //    private String link = "http://185.179.168.5:900/Hotspot.aspx";
    private String txt = "با این لینک تو ماهان نت ثبت نام کن و از اینترنت خوب لذت ببر";


    public AdapterDrawerRecycler(List<ModelDrawerItems> list, Context context) throws Exception {
        this.list = list;
        this.context = context;
    }

    @Override
    public DrawerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_drawre_item, parent, false);
        return new DrawerRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final DrawerRecyclerViewHolder holder, final int position) {
        holder.imgImage.setImageResource(list.get(position).getImage());

//        if (G.currentLicense.Chargeonline) {
//            holder.layout.setVisibility(View.GONE);
//        }else {
//            holder.layout.setVisibility(View.VISIBLE);
//        }
        holder.txtText.setText(list.get(position).getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (list.get(position).getTag()) {
                    case profile:
                        G.context.startActivity(new Intent(context, ActivityShowUserInfo.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case connectionReport:
                        G.context.startActivity(new Intent(context, ActivityShowConnections.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case onlineSharj:
                        if (G.currentLicense != null) {
                            if (G.currentLicense.Chargeonline) {
                                G.context.startActivity(new Intent(context, ActivityChargeOnline.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                U.toast("امکان شارژ آنلاین برای شما فعال نمیباشد.");
                            }
                        }
                        break;
                    case payReport:
                        G.context.startActivity(new Intent(context, ActivityPayments.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case consumechart:
                        new DialogClass().showMessageDialog(context.getString(R.string.alert), context.getString(R.string.item_available_in_future));

//                        G.context.startActivity(new Intent(context, ActivityShowGraph.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case speedTest:
                        new DialogClass().showMessageDialog(context.getString(R.string.alert), context.getString(R.string.item_available_in_future));
                        break;

                    case support:
                        G.context.startActivity(new Intent(context, ActivityShowTickets.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case inviteFriends:// davat az doostan
                        WebService.sendGetRepresenterURL();
//                        sendText();
                        break;

                    case pointReports:
                        G.context.startActivity(new Intent(context, ActivityShowClubScores.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case messages:
                        G.context.startActivity(new Intent(context, ActivityShowNotify.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case games:
                        Intent intent = new Intent(G.context, ActivityPoints.class);
                        G.context.startActivity(intent);
//                        new DialogClass().showMessageDialog(context.getString(R.string.alert), context.getString(R.string.item_available_in_future));
                        break;
                    case feshfeshe:
                        G.context.startActivity(new Intent(context, ActivityShowFeshfeshe.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case news:
                        G.context.startActivity(new Intent(context, ActivityShowNews.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case exit:
                        DialogClass dialogExit = new DialogClass();
                        dialogExit.showExitDialog();
                        break;
                    case factors:
                        G.context.startActivity(new Intent(context, ActivityShowFactors.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case changePassword:
                        G.context.startActivity(new Intent(context, ActivityChangePassword.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case trafficSplit:
                        G.context.startActivity(new Intent(context, ActivityShowTrafficeSplite.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class DrawerRecyclerViewHolder extends RecyclerView.ViewHolder {
        ir.aspacrm.my.app.mahanet.component.PersianTextViewNormal txtText;
        ImageView imgImage;
        LinearLayout layout;

        public DrawerRecyclerViewHolder(View itemView) {
            super(itemView);
            txtText = (ir.aspacrm.my.app.mahanet.component.PersianTextViewNormal) itemView.findViewById(R.id.txtTitle);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
            layout = (LinearLayout) itemView.findViewById(R.id.lay);

        }

    }


    private void sendText() {
        try {
            link = G.currentUser.ispUrl + "/User/signup/" + Encrypt(G.currentUserInfo.username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("Des/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, txt + "\n\n" + link);
        try {
            context.startActivity(Intent.createChooser(sendIntent, context.getString(R.string.choose_sender)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (android.content.ActivityNotFoundException ex) {
            new DialogClass().showMessageDialog(context.getString(R.string.error), context.getString(R.string.send_failure));
        }
    }

    public static String Encrypt(String text) throws Exception {
        // Sending side
        byte[] data = text.getBytes("UTF-8");
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;
    }


    public static String StringToHex(byte[] ba) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < ba.length; i++)
            str.append(String.format("%x", ba[i]));
        return str.toString();
    }
}
