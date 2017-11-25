package ir.aspacrm.my.app.mahanet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.R;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.enums.EnumStatusFactor;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedFactorMoreDetail;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedStartFactor;
import ir.aspacrm.my.app.mahanet.model.Factor;

public class AdapterFactor extends RecyclerView.Adapter<AdapterFactor.FactorViewHolder> {

    List<Factor> factors;
    Context context;

    public AdapterFactor(List<Factor> factors, Context context) {
        this.factors = factors;
        this.context = context;
    }

    @Override
    public FactorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.l_factor_item, parent, false);
        return new FactorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FactorViewHolder holder, int position) {
        final Factor factor = factors.get(position);
        String pattern = "#,###";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        long b = Long.parseLong(factor.amount);
        holder.txtFactorAmount.setText("" + myFormatter.format(b) + " " + G.currentUserInfo.unit);
        holder.txtFactorCode.setText("" + factor.factorId);
        if (factor.des.length() == 0) {
            holder.layFactorDescription.setVisibility(View.GONE);
        } else {
            holder.layFactorDescription.setVisibility(View.VISIBLE);
            holder.txtFactorDes.setText("" + factor.des);
        }
        holder.txtFactorDiscount.setText("" + factor.discount + " " + G.currentUserInfo.unit);
        holder.txtFactorDate.setText("" + factor.date);
        holder.txtFactorEndDate.setText("" + factor.expireDate);
        holder.txtFactorStartDate.setText("" + factor.startDate);
        holder.txtFactorTax.setText("" + factor.tax + " " + G.currentUserInfo.unit);

        long a = Long.parseLong(factor.price);
        holder.txtFactorPrice.setText("" + myFormatter.format(a) + " " + G.currentUserInfo.unit);

        holder.layMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedFactorMoreDetail(factor.factorId));
            }
        });

        holder.layBtnStartFactor.setVisibility(View.GONE);

        switch (factor.status) {
            case EnumStatusFactor.CalFactor:
                holder.layBtnStartFactor.setVisibility(View.GONE);
                break;
            case EnumStatusFactor.UnStarted:
                holder.layBtnStartFactor.setVisibility(View.VISIBLE);
                holder.layBtnStartFactor.setCardBackgroundColor(G.context.getResources().getColor(R.color.txt_blue));
//                holder.layBtnStartFactorColor. setBackgroundColor(G.context.getResources().getColor(R.color.txt_blue));
                holder.layBtnStartFactor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new EventOnClickedStartFactor(factor.factorId));
                    }
                });
                break;
            case EnumStatusFactor.UnStartedCantStart:
                holder.layBtnStartFactor.setVisibility(View.GONE);
                holder.layBtnStartFactor.setVisibility(View.VISIBLE);
                holder.layBtnStartFactor.setAlpha(0.5f);
                holder.layBtnStartFactor.setEnabled(false);
                holder.layBtnStartFactor. setCardBackgroundColor(G.context.getResources().getColor(R.color.txt_blue_transparent));
//                holder.layBtnStartFactorColor. setBackgroundColor(G.context.getResources().getColor(R.color.txt_blue_transparent));

                break;
            case EnumStatusFactor.Started:
                holder.layBtnStartFactor.setVisibility(View.GONE);
                break;


        }
    }

    @Override
    public int getItemCount() {
        return factors.size();
    }

    public class FactorViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtFactorAmount)
        TextView txtFactorAmount;
        @Bind(R.id.txtFactorCode)
        TextView txtFactorCode;
        @Bind(R.id.txtFactorDes)
        TextView txtFactorDes;
        @Bind(R.id.txtFactorDiscount)
        TextView txtFactorDiscount;
        @Bind(R.id.txtFactorEndDate)
        TextView txtFactorEndDate;
        @Bind(R.id.txtFactorStartDate)
        TextView txtFactorStartDate;
        @Bind(R.id.txtFactorPrice)
        TextView txtFactorPrice;
        @Bind(R.id.txtFactorTax)
        TextView txtFactorTax;
        @Bind(R.id.txtFactorDate)
        TextView txtFactorDate;
        @Bind(R.id.layMoreDetail)
        LinearLayout layMoreDetail;
        @Bind(R.id.layFactorDescription)
        LinearLayout layFactorDescription;
        @Bind(R.id.layBtnStartFactor)
        CardView layBtnStartFactor;
        @Bind(R.id.card_view)
        CardView card_view;

        @Bind(R.id.layBtnStartFactorColor)
        LinearLayout layBtnStartFactorColor;

        public FactorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<Factor> data) {
        factors = data;
        notifyDataSetChanged();
        Logger.d("AdapterPayment : payments size is " + factors.size());
    }
}
