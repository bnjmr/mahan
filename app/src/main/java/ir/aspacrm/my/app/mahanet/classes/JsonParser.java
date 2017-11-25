package ir.aspacrm.my.app.mahanet.classes;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnChangePasswordResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnExKeyMistake;
import ir.aspacrm.my.app.mahanet.events.EventOnGetAddTicketDetailsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetAddTicketResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetAdvsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetAlertResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetAvatarImageResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetBankListResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetBankPageResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForCountCategoryResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForLoadCategory;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForLoadGroups;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForLoadPackages;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForPayResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForSelectPackage;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForTamdid;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineMainItem;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCheckChargeOnlineClub;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCheckOrderIdResultResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCheckTarazResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCityGroupsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCityResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetClubScoreResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetClubScoresResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetConnectionResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCountNotify;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCurrentFeshFesheResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCurrentTrafficSplite;
import ir.aspacrm.my.app.mahanet.events.EventOnGetEndFeshFeshesResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorCallBankPage;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorCheckTaraz;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetBankList;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetCities;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetFactorDetail;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorPayFactorFromCredit;
import ir.aspacrm.my.app.mahanet.events.EventOnGetFactorDetailsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetFactorResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetGraphResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetIspInfoLoginResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetIspInfoResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetIspListResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetIspUrlResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetLoadFeshFeshesResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetLocations;
import ir.aspacrm.my.app.mahanet.events.EventOnGetLoginResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetNewsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetNotifiesResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetPayFactorFromCreditResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetPayOnlineForPayResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetPayOnlineResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetPaymentResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetPollResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetRegConnectAllowResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetRegConnectionResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetRegisterCustomerResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetReperesenterURL;
import ir.aspacrm.my.app.mahanet.events.EventOnGetSelectFactorResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetStartFactorResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetStartFeshFeshesResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetTicketDetailsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetTicketResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetUnitResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetUpdateResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetUserAccountInfoResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetUserInfoResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetUserLicenseResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetVisitMobileResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnLoadTrafficSplitNotMain;
import ir.aspacrm.my.app.mahanet.events.EventOnRememberPasswordResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnSetAdsRepResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnSetPollResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnSuccessGetCities;
import ir.aspacrm.my.app.mahanet.events.EventOnSuccessGetRegions;
import ir.aspacrm.my.app.mahanet.events.EventOnSuccessRegisterCustomer;
import ir.aspacrm.my.app.mahanet.events.EventOnTicketCodeResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnUploadImageResponse;
import ir.aspacrm.my.app.mahanet.gson.AddScoreResponse;
import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineCategory;
import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineGroup;
import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineGroupPackage;
import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineMainItemResponse;
import ir.aspacrm.my.app.mahanet.gson.CityGroupResponse;
import ir.aspacrm.my.app.mahanet.gson.CityResponse;
import ir.aspacrm.my.app.mahanet.gson.ClubScoresResponse;
import ir.aspacrm.my.app.mahanet.gson.CurrentFeshFesheResponse;
import ir.aspacrm.my.app.mahanet.gson.CurrentTrraficSplite;
import ir.aspacrm.my.app.mahanet.gson.FactorDetailResponse;
import ir.aspacrm.my.app.mahanet.gson.FactorResponse;
import ir.aspacrm.my.app.mahanet.gson.GetAdvsResponse;
import ir.aspacrm.my.app.mahanet.gson.GetConnectionsResponse;
import ir.aspacrm.my.app.mahanet.gson.GetGraphResponse;
import ir.aspacrm.my.app.mahanet.gson.GetIspInfoResponse;
import ir.aspacrm.my.app.mahanet.gson.GetPollResponse;
import ir.aspacrm.my.app.mahanet.gson.GetUnitsResponse;
import ir.aspacrm.my.app.mahanet.gson.ISPInfoLoginResponse;
import ir.aspacrm.my.app.mahanet.gson.LoadBanksResponse;
import ir.aspacrm.my.app.mahanet.gson.LoadFeshFeshesResponse;
import ir.aspacrm.my.app.mahanet.gson.LocationsResponse;
import ir.aspacrm.my.app.mahanet.gson.LoginResponse;
import ir.aspacrm.my.app.mahanet.gson.NewsResponse;
import ir.aspacrm.my.app.mahanet.gson.NotifyResponse;
import ir.aspacrm.my.app.mahanet.gson.PayFactorFromCreditResponse;
import ir.aspacrm.my.app.mahanet.gson.PaymentResponse;
import ir.aspacrm.my.app.mahanet.gson.RegConnectResponse;
import ir.aspacrm.my.app.mahanet.gson.SearchISPResponse;
import ir.aspacrm.my.app.mahanet.gson.SelectFactorResponse;
import ir.aspacrm.my.app.mahanet.gson.TicketCodeResponse;
import ir.aspacrm.my.app.mahanet.gson.TicketDetailsResponse;
import ir.aspacrm.my.app.mahanet.gson.TicketResponse;
import ir.aspacrm.my.app.mahanet.gson.TrafficSplitNotMainModel;
import ir.aspacrm.my.app.mahanet.gson.UserInfoResponse;
import ir.aspacrm.my.app.mahanet.model.Account;
import ir.aspacrm.my.app.mahanet.model.ClubScore;
import ir.aspacrm.my.app.mahanet.model.Connection;
import ir.aspacrm.my.app.mahanet.model.Factor;
import ir.aspacrm.my.app.mahanet.model.FactorDetail;
import ir.aspacrm.my.app.mahanet.model.Feshfeshe;
import ir.aspacrm.my.app.mahanet.model.Graph;
import ir.aspacrm.my.app.mahanet.model.Info;
import ir.aspacrm.my.app.mahanet.model.License;
import ir.aspacrm.my.app.mahanet.model.Locations;
import ir.aspacrm.my.app.mahanet.model.ModelCities;
import ir.aspacrm.my.app.mahanet.model.ModelRegions;
import ir.aspacrm.my.app.mahanet.model.ModelRegisterCustomerResponse;
import ir.aspacrm.my.app.mahanet.model.News;
import ir.aspacrm.my.app.mahanet.model.Notify;
import ir.aspacrm.my.app.mahanet.model.Payment;
import ir.aspacrm.my.app.mahanet.model.Ticket;
import ir.aspacrm.my.app.mahanet.model.TicketCodes;
import ir.aspacrm.my.app.mahanet.model.TicketDetail;
import ir.aspacrm.my.app.mahanet.model.Unit;
import ir.aspacrm.my.app.mahanet.model.getUpdate;

import static ir.aspacrm.my.app.mahanet.G.gson;

public class JsonParser {

    //    private static String adsRepResponse;
    public static void getIspListResponse(String json) {
        Logger.d("JsonParser : getIspListResponse json is  " + json);
        try {
            SearchISPResponse[] isps = gson.fromJson(json, SearchISPResponse[].class);
            EventBus.getDefault().post(new EventOnGetIspListResponse(Arrays.asList(isps)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getIspListResponse() " + e.getMessage());
        }
    }

    public static void getIspInfoResponse(String json) {
        Logger.d("JsonParser : getIspInfoResponse json is  " + json);
        try {
            ISPInfoLoginResponse ispInfo = gson.fromJson(json, ISPInfoLoginResponse.class);
            EventBus.getDefault().post(new EventOnGetIspInfoLoginResponse(ispInfo));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getIspInfoResponse() " + e.getMessage());
        }
    }

    public static void getLoginResponse(String json, long ispId, String ispUrl) {
        Logger.d("JsonParser : getLoginResponse json is  " + json);
        //{"Token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJVc2VybmFtZSI6ImphaGFubWlyIiwiSUQiOjEzOCwiRXhLZXkiOiIyMjBjNzdhZjAyZjhhZDg1NjFiMTUwZDkzMDAwZGRmZiIsIklzV2ViIjpmYWxzZSwiRW1wdHlUb2tlbiI6MzU1MjY4OCwiQ3JlYXRlZFRpbWUiOiIyMDE3LTA5LTI1VDExOjIwOjU0LjQzMzUzODIrMDM6MzAifQ.k3wpvYk8qEx1MYQ4dfqpNjghQMqIK3zF2jS2Ghw5EoHamDx7C0ONi-pjpjpC6Q4f","Message":"","Result":4}
        try {
            LoginResponse response = gson.fromJson(json, LoginResponse.class);
            /** be dalil inke ma meghdar ispUrl ra nadarim mabaghi amaliat ra dar safheye login anjam midahim.**/
            EventBus.getDefault().post(new EventOnGetLoginResponse(response, ispId, ispUrl));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getLoginResponse() " + e.getMessage());
        }
    }

    public static void getUserLicenseResponse(String json) {
        Logger.d("JsonParser : getUserLicenseResponse json is  " + json);
        try {
            License license = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(json, License.class);

            if (license.Result == EnumResponse.OK) {
                License existLicenseCurrentUser = new Select().from(License.class).executeSingle();
                if (existLicenseCurrentUser == null) {
                    license.save();
                    G.currentLicense = license;
                } else {
                    new Delete().from(License.class).execute();
                    license.save();
                    G.currentLicense = license;
                }


            } else if (license.Result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            EventBus.getDefault().post(new EventOnGetUserLicenseResponse());
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getUserLicenseResponse() " + e.getMessage());
        }
    }

    public static void getUserAccountInfoResponse(String json) {
        Logger.d("JsonParser : getUserAccountInfoResponse json is  " + json);
        try {
            Account accounts = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(json, Account.class);
            if (accounts != null) {
                Account existAccountCurrentUser = new Select().from(Account.class).executeSingle();
                if (existAccountCurrentUser != null) {
                    new Delete().from(Account.class).execute();
                    accounts.save();
                } else {
                    accounts.save();
                }
                G.currentAccount = new Select().from(Account.class).executeSingle();
                if (accounts.Result == EnumResponse.ExkeyMistake) {
                    EventBus.getDefault().post(new EventOnExKeyMistake());
                    return;
                }
            }
            EventBus.getDefault().post(new EventOnGetUserAccountInfoResponse(accounts));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getUserAccountInfoResponse() " + e.getMessage());
        }
    }

    public static void getGetUserInfoResponse(String json) {
        Logger.d("JsonParser : getGetUserInfoResponse json is  " + json);
        try {
            UserInfoResponse response = gson.fromJson(json, UserInfoResponse.class);
            if (response.result == EnumResponse.OK) {
                Info info = new Select().from(Info.class).executeSingle();
                if (info == null) {
                    Info newInfo = new Info();
                    newInfo.fullName = response.fullName;
                    newInfo.username = response.username;
                    newInfo.mobileNo = response.mobileNo;
                    newInfo.resellerName = response.resellerName;
                    newInfo.firstCon = response.firstCon;
                    newInfo.lastCon = response.lastCon;
                    newInfo.serviceType = response.serviceType;
                    newInfo.status = response.status;
                    newInfo.unit = response.unit;
                    newInfo.save();
                    G.currentUserInfo = newInfo;
                } else {
                    info.fullName = response.fullName;
                    info.username = response.username;
                    info.mobileNo = response.mobileNo;
                    info.resellerName = response.resellerName;
                    info.firstCon = response.firstCon;
                    info.lastCon = response.lastCon;
                    info.serviceType = response.serviceType;
                    info.status = response.status;
                    info.unit = response.unit;
                    info.save();

                    G.currentUserInfo = info;
                }
            } else if (response.result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }


            EventBus.getDefault().post(new EventOnGetUserInfoResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getGetUserInfoResponse() " + e.getMessage());
        }
    }

    public static void sendRememberPasswordResponse(String json) {
        Logger.d("JsonParser : sendRememberPasswordResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            String message = jsonObject.getString("Message");
            if (status == EnumResponse.OK)
                message = "درخواست شما برای بازیابی رمز عبور ارسال شد.";
            else
                message = message + ".";
            EventBus.getDefault().post(new EventOnRememberPasswordResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on sendRememberPasswordResponse() " + e.getMessage());
        }
    }

    public static void getChangePasswordResponse(String json) {
        Logger.d("JsonParser : getChangePasswordResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            String message = jsonObject.getString("Message");
            EventBus.getDefault().post(new EventOnChangePasswordResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChangePasswordResponse() " + e.getMessage());
        }
    }

    public static void getPaymentResponse(String json) {
        Logger.d("JsonParser : getPaymentResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int Result = jsonObject.getInt("Result");
            if (Result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("payments");
            String paymentsData = jsonArray.toString();
            PaymentResponse[] payments = gson.fromJson(paymentsData, PaymentResponse[].class);
            List<Payment> paymentList = new ArrayList<>();
            U.deletePaymentTableItem();
            for (PaymentResponse payment : payments) {
                Payment newPayment = new Payment();
                newPayment.date = payment.Date;
                newPayment.des = payment.Des;
                newPayment.price = payment.Price;
                newPayment.type = payment.Type;
                newPayment.save();
                paymentList.add(newPayment);
            }
            EventBus.getDefault().post(new EventOnGetPaymentResponse(paymentList));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPaymentResponse() " + e.getMessage());
        }
    }

    public static void getFactorResponse(String json) {
        Logger.d("JsonParser : getFactorResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int Result = jsonObject.getInt("Result");
            if (Result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("factors");
            String s = jsonArray.toString();
            FactorResponse[] response = gson.fromJson(s, FactorResponse[].class);
            /** delete all item in Factor table and save new factor on it.*/
            U.deleteTicketTableItem();
            List<Factor> factors = new ArrayList<>();
            for (FactorResponse factor : response) {
                Factor newFactor = new Factor();
                newFactor.factorId = factor.code;
                newFactor.date = factor.Date;
                newFactor.startDate = factor.StartDate;
                newFactor.expireDate = factor.ExpireDate;
                newFactor.price = factor.Price;
                newFactor.des = factor.Des;
                newFactor.status = factor.Status;
                newFactor.discount = factor.Discount;
                newFactor.tax = factor.Tax;
                newFactor.amount = factor.Amount;
                newFactor.save();
                factors.add(newFactor);
            }
            EventBus.getDefault().post(new EventOnGetFactorResponse(factors));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getFactorResponse() " + e.getMessage());
        }
    }

    public static void getFactorDetailResponse(String json, long factorCode) {
        Logger.d("JsonParser : getFactorDetailResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.optJSONObject("factor");
            int Result = jsonObject.getInt("Result");
            FactorDetailResponse[] response;
            if (Result != EnumResponse.OK) {
                if (Result == EnumResponse.ExkeyMistake) {
                    EventBus.getDefault().post(new EventOnExKeyMistake());
                    return;
                }
                response = new FactorDetailResponse[0];
            } else {
                JSONArray jsonArray = jsonObject.getJSONArray("FactorDetails");
                String s = jsonArray.toString();
                response = gson.fromJson(s, FactorDetailResponse[].class);
                /** delete all item in FactorDetail table and save new FactorDetail on it.*/
                U.deleteFactorDetailTableItem(factorCode);
                for (FactorDetailResponse factorDetail : response) {
                    FactorDetail newFactorDetail = new FactorDetail();
                    newFactorDetail.ParentId = factorCode;
                    newFactorDetail.Name = factorDetail.Name;
                    newFactorDetail.Des = factorDetail.Des;
                    newFactorDetail.Price = factorDetail.Price;
                    newFactorDetail.save();
                }
            }

            EventBus.getDefault().post(new EventOnGetFactorDetailsResponse(new ArrayList<FactorDetailResponse>(Arrays.asList(response))));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getFactorDetailResponse() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorGetFactorDetail(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED, factorCode));
        }
    }

    public static void getStartFactorResponse(String json) {
        Logger.d("JsonParser : getStartFactorResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }

            String message = jsonObject.getString("Message");
            EventBus.getDefault().post(new EventOnGetStartFactorResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getStartFactorResponse() " + e.getMessage());
        }
    }

    public static void getUnitsResponse(String json) {
        Logger.d("JsonParser : getUnitsResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("units");
            int Result = jsonObject.getInt("Result");
            if (Result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            String d = jsonArray.toString();
            GetUnitsResponse[] response = gson.fromJson(d, GetUnitsResponse[].class);
            /** delete all item in UnitName table and save new UnitName on it.*/
            U.deleteUnitTableItem();
            for (GetUnitsResponse unit : response) {
                Unit newUnit = new Unit();
                newUnit.code = unit.code;
                newUnit.name = unit.Name;
                newUnit.save();
            }
            EventBus.getDefault().post(new EventOnGetUnitResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getUnitsResponse() " + e.getMessage());
        }
    }

    public static void getAddTicketResponse(String json) {
        Logger.d("JsonParser : getAddTicketResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            long code = jsonObject.getLong("code");
            EventBus.getDefault().post(new EventOnGetAddTicketResponse(status, code));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getAddTicketResponse() " + e.getMessage());
        }
    }

    public static void getAddTicketDetailResponse(String json) {
        Logger.d("JsonParser : getAddTicketDetailResponse json is  " + json);
        try {

            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            String message = jsonObject.getString("Message");
            EventBus.getDefault().post(new EventOnGetAddTicketDetailsResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getAddTicketDetailResponse() " + e.getMessage());
        }
    }

    public static void getTicketsResponse(String json) {
        Logger.d("JsonParser : getTicketsResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("Tickets");
            String s = jsonArray.toString();
            TicketResponse[] response = gson.fromJson(s, TicketResponse[].class);
            /** delete all item in ticket table and save new tickets on it.*/
            U.deleteTicketTableItem();
            for (TicketResponse ticket : response) {
                Ticket newTicket = new Ticket();
                newTicket.code = ticket.code;
                newTicket.PriorityName = ticket.PriorityName;
                newTicket.date = ticket.Date;
                newTicket.open = ticket.Open;
                newTicket.priority = ticket.Priority;
                newTicket.state = ticket.State;
                newTicket.title = ticket.Title;
                newTicket.CountUnread = ticket.CountUnread;
                newTicket.save();
            }
            EventBus.getDefault().post(new EventOnGetTicketResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getTicketsResponse() " + e.getMessage());
        }
    }

    public static void getTicketDetailsResponse(String json, long ticketCode) {
        Logger.d("JsonParser : getTicketDetailsResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("TicketDetails");
            String s = jsonArray.toString();
            TicketDetailsResponse[] response = gson.fromJson(s, TicketDetailsResponse[].class);
            /** delete all item in ticketDetail table with special parentCode and save new ticketDetails on it.*/
            U.deleteTicketDetailTableItem(ticketCode);
            for (TicketDetailsResponse ticketDetail : response) {
                TicketDetail newTicketDetail = new TicketDetail();
                newTicketDetail.parentCode = ticketCode;
                newTicketDetail.User = ticketDetail.User;
                newTicketDetail.Date = ticketDetail.Date;
                newTicketDetail.State = ticketDetail.State;
                newTicketDetail.Des = ticketDetail.Des;
                newTicketDetail.UnitName = ticketDetail.UnitName;
                newTicketDetail.UnitCode = ticketDetail.Unit;
                newTicketDetail.save();
            }
            EventBus.getDefault().post(new EventOnGetTicketDetailsResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getTicketDetailsResponse() " + e.getMessage());
        }
    }

    public static void getRegConnectAllowResponse(String json) {
        Logger.d("JsonParser : getRegConnectAllowResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            EventBus.getDefault().post(new EventOnGetRegConnectAllowResponse(status));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getRegConnectAllowResponse() " + e.getMessage());
        }
    }

    public static void getRegConnectResponse(String json) {
        Logger.d("JsonParser : getRegConnectResponse json is  " + json);
        try {
            RegConnectResponse response = gson.fromJson(json, RegConnectResponse.class);
            if (response.Result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            EventBus.getDefault().post(new EventOnGetRegConnectionResponse(Collections.singletonList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getRegConnectResponse() " + e.getMessage());
        }
    }

    public static void getConnectionsResponse(String json) {
        //  {"conections":[{"code":0,"StartTime":"-----","EndTime":"-----","Duration":"179:51","Send":276.0,"Recieve":1961.0,"TrafficUsed":2183.0,"TrafficFree":55.0,"TrafficAll":2237.0,"Reason":"Sum"},{"code":0,"StartTime":"1396/07/14 18:02","EndTime":"1396/07/16 08:09","Duration":"38:7","Send":69.38006,"Recieve":467.713776,"TrafficUsed":490.7537,"TrafficFree":46.34012,"TrafficAll":537.0938,"Reason":null},{"code":0,"StartTime":"1396/07/14 00:21","EndTime":"1396/07/14 13:37","Duration":"13:15","Send":26.3907661,"Recieve":358.175232,"TrafficUsed":383.1782,"TrafficFree":1.38781738,"TrafficAll":384.566,"Reason":"Port-Error"},{"code":0,"StartTime":"1396/07/13 14:28","EndTime":"1396/07/14 00:18","Duration":"9:49","Send":18.82462,"Recieve":203.6521,"TrafficUsed":222.4767,"TrafficFree":1.52587891E-05,"TrafficAll":222.476715,"Reason":"Port-Error"},{"code":0,"StartTime":"1396/07/13 10:38","EndTime":"1396/07/13 14:28","Duration":"3:49","Send":4.08517361,"Recieve":23.294178,"TrafficUsed":27.3794,"TrafficFree":0.0,"TrafficAll":27.3793526,"Reason":"Admin-Reset"},{"code":0,"StartTime":"1396/07/13 03:22","EndTime":"1396/07/13 10:38","Duration":"7:15","Send":2.10677624,"Recieve":23.4943123,"TrafficUsed":25.4437,"TrafficFree":0.157388687,"TrafficAll":25.60109,"Reason":"Admin-Reset"},{"code":0,"StartTime":"1396/07/12 16:53","EndTime":"1396/07/13 01:26","Duration":"8:33","Send":23.979229,"Recieve":172.688385,"TrafficUsed":196.6676,"TrafficFree":1.52587891E-05,"TrafficAll":196.667618,"Reason":"Port-Error"},{"code":0,"StartTime":"1396/07/12 16:50","EndTime":"1396/07/12 16:53","Duration":"0:3","Send":0.0006599426,"Recieve":0.00106716156,"TrafficUsed":0.0017,"TrafficFree":2.71041645E-05,"TrafficAll":0.00172710419,"Reason":"Admin-Reset"},{"code":0,"StartTime":"1396/07/12 16:34","EndTime":"1396/07/12 16:37","Duration":"0:2","Send":0.0,"Recieve":0.0,"TrafficUsed":0.0,"TrafficFree":0.0,"TrafficAll":0.0,"Reason":"Maximum check online fails reached"},{"code":0,"StartTime":"1396/07/12 15:08","EndTime":"1396/07/12 15:09","Duration":"0:1","Send":0.0003643036,"Recieve":0.0009098053,"TrafficUsed":0.0013,"TrafficFree":0.0,"TrafficAll":0.00127410889,"Reason":"Port-Error"},{"code":0,"StartTime":"1396/07/12 14:51","EndTime":"1396/07/12 14:55","Duration":"0:4","Send":0.0,"Recieve":0.0,"TrafficUsed":0.0,"TrafficFree":0.0,"TrafficAll":0.0,"Reason":"Maximum check online fails reached"},{"code":0,"StartTime":"1396/07/12 14:31","EndTime":"1396/07/12 14:32","Duration":"0:1","Send":0.000948905945,"Recieve":0.00618839264,"TrafficUsed":0.0071,"TrafficFree":3.72985378E-05,"TrafficAll":0.00713729858,"Reason":"Port-Error"},{"code":0,"StartTime":"1396/07/12 13:34","EndTime":"1396/07/12 13:37","Duration":"0:3","Send":0.0,"Recieve":0.0,"TrafficUsed":0.0,"TrafficFree":0.0,"TrafficAll":0.0,"Reason":"Maximum check online fails reached"},{"code":0,"StartTime":"1396/07/12 13:23","EndTime":"1396/07/12 13:26","Duration":"0:2","Send":0.0108108521,"Recieve":0.0235061646,"TrafficUsed":0.0343,"TrafficFree":1.70171261E-05,"TrafficAll":0.0343170166,"Reason":"Port-Error"},{"code":0,"StartTime":"1396/07/12 13:08","EndTime":"1396/07/12 13:23","Duration":"0:14","Send":0.473489761,"Recieve":3.34997082,"TrafficUsed":3.8235,"TrafficFree":0.0,"TrafficAll":3.82346058,"Reason":"Port-Error"},{"code":0,"StartTime":"1396/07/12 12:23","EndTime":"1396/07/12 12:27","Duration":"0:3","Send":0.0,"Recieve":0.0,"TrafficUsed":0.0,"TrafficFree":0.0,"TrafficAll":0.0,"Reason":"Maximum check online fails reached"},{"code":0,"StartTime":"1396/07/12 09:50","EndTime":"1396/07/12 11:36","Duration":"1:46","Send":2.8936367,"Recieve":24.7088833,"TrafficUsed":27.6025,"TrafficFree":1.90734863E-05,"TrafficAll":27.60252,"Reason":"Port-Error"},{"code":0,"StartTime":"1396/07/12 09:46","EndTime":"1396/07/12 09:48","Duration":"0:2","Send":0.0,"Recieve":0.0,"TrafficUsed":0.0,"TrafficFree":0.0,"TrafficAll":0.0,"Reason":"Maximum check online fails reached"},{"code":0,"StartTime":"1396/07/12 09:38","EndTime":"1396/07/12 09:40","Duration":"0:2","Send":0.0,"Recieve":0

        Logger.d("JsonParser : getConnectionsResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }

            JSONArray jsonArray = jsonObject.getJSONArray("conections");
            String s = jsonArray.toString();

            GetConnectionsResponse[] response = gson.fromJson(s, GetConnectionsResponse[].class);
            List<Connection> connections = new ArrayList<>();
            U.deleteConnectionTableItem();
            for (GetConnectionsResponse connection : response) {
                Connection newConnection = new Connection();
                newConnection.startTime = connection.StartTime;
                newConnection.endTime = connection.EndTime;
                newConnection.Duration = connection.Duration;
                newConnection.Send = connection.Send;
                newConnection.Recieve = connection.Recieve;
                newConnection.TrafficUsed = connection.TrafficUsed;
                newConnection.Reason = connection.Reason;
                newConnection.TrafficAll = connection.TrafficAll;
                newConnection.save();
                connections.add(newConnection);
            }
            EventBus.getDefault().post(new EventOnGetConnectionResponse(connections));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getConnectionsResponse() " + e.getMessage());
        }
    }

    public static void getGraphResponse(String json) {
        Logger.d("JsonParser : getGraphResponse json is  " + json);
        try {
            GetGraphResponse[] response = gson.fromJson(json, GetGraphResponse[].class);
            Graph newGraph = new Graph();
            if (response.length > 0) {
                new Delete().from(Graph.class).execute();
                newGraph.xData = response[0].XData;
                newGraph.yData = response[0].YData;
                newGraph.save();
            }
            EventBus.getDefault().post(new EventOnGetGraphResponse(newGraph));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getGraphResponse() " + e.getMessage());
        }
    }

    public static void getChargeOnlineResponse(String html) {
        Logger.d("JsonParser : getChargeOnlineResponse html is  " + html);
        try {
            EventBus.getDefault().post(new EventOnGetChargeOnlineResponse(html));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineResponse() " + e.getMessage());
        }
    }

    public static void getPayOnlineResponse(String html) {
        Logger.d("JsonParser : getPayOnlineResponse html is  " + html);
        try {
            EventBus.getDefault().post(new EventOnGetPayOnlineResponse(html));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPayOnlineResponse() " + e.getMessage());
        }
    }

    public static void getClubScoreResponse(String json) {
        Logger.d("JsonParser : getClubScoreResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("Result");

            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            int score = jsonObject.getInt("Score");
            EventBus.getDefault().post(new EventOnGetClubScoreResponse(result, score));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getClubScoreResponse() " + e.getMessage());
        }
    }

    public static void getClubScoresResponse(String json) {
        Logger.d("JsonParser : getClubScoresResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("scores");
            String s = jsonArray.toString();
            ClubScoresResponse[] response = gson.fromJson(s, ClubScoresResponse[].class);
            List<ClubScore> clubScores = new ArrayList<>();
            /** delete all item in ticketDetail table with special parentCode and save new ticketDetails on it.*/
            U.deleteClubScoreTableItem();
            for (ClubScoresResponse score : response) {
                ClubScore newClubScore = new ClubScore();
                newClubScore.title = score.Title;
                newClubScore.des = score.Des;
                newClubScore.score = score.Score;
                newClubScore.date = score.Date;
                newClubScore.save();
                clubScores.add(newClubScore);
            }
            EventBus.getDefault().post(new EventOnGetClubScoresResponse(clubScores));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getClubScoresResponse() " + e.getMessage());
        }
    }

    public static void getLoadFeshFeshesResponse(String json) {
        Logger.d("JsonParser : getLoadFeshFeshesResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("feshfeshes");
            String s = jsonArray.toString();
            LoadFeshFeshesResponse[] response = gson.fromJson(s, LoadFeshFeshesResponse[].class);
            List<Feshfeshe> feshfesheList = new ArrayList<>();
            /** delete all item in ticketDetail table with special parentCode and save new ticketDetails on it.*/
            U.deleteFeshfesheTableItem();
            for (LoadFeshFeshesResponse feshfeshe : response) {
                Feshfeshe newFeshfeshe = new Feshfeshe();
                newFeshfeshe.packageName = feshfeshe.Package;
                newFeshfeshe.code = feshfeshe.Code;
                newFeshfeshe.started = feshfeshe.Started;
                newFeshfeshe.hour = feshfeshe.hour;
                newFeshfeshe.day = feshfeshe.day;
                newFeshfeshe.traffic = feshfeshe.traffic;
                newFeshfeshe.save();
                feshfesheList.add(newFeshfeshe);
            }
            EventBus.getDefault().post(new EventOnGetLoadFeshFeshesResponse(feshfesheList));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getLoadFeshFeshesResponse() " + e.getMessage());
        }
    }

    public static void getStartFeshFeshesResponse(String json) {
        Logger.d("JsonParser : getStartFeshFeshesResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            String message = jsonObject.getString("Message");
            EventBus.getDefault().post(new EventOnGetStartFeshFeshesResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getStartFeshFeshesResponse() " + e.getMessage());
        }
    }

    public static void getCurrentFeshFeshesResponse(String json) {
        Logger.d("JsonParser : getCurrentFeshFeshesResponse json is  " + json);
        //{"Exist":false,"Expire":null,"Traffic":0,"Message":"فشفشه فعال موجود نیست","Result":4}
        try {
            JSONObject jsonObject = new JSONObject(json);
            boolean Exist = jsonObject.getBoolean("Exist");
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            CurrentFeshFesheResponse response;
            if (!Exist) {
                response = new CurrentFeshFesheResponse();
            } else {
                String s = jsonObject.toString();
                response = gson.fromJson(s, CurrentFeshFesheResponse.class);
            }

            EventBus.getDefault().post(new EventOnGetCurrentFeshFesheResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCurrentFeshFeshesResponse() " + e.getMessage());
        }
    }

    public static void getEndFeshFeshesResponse(String json) {
        Logger.d("JsonParser : getEndFeshFeshesResponse json is  " + json);
        try {

            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            String message = jsonObject.getString("Message");
            EventBus.getDefault().post(new EventOnGetEndFeshFeshesResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getEndFeshFeshesResponse() " + e.getMessage());
        }
    }

    public static void getNewsResponse(String json) {
        Logger.d("JsonParser : getNewsResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("news");
            String s = jsonArray.toString();
            NewsResponse[] response = gson.fromJson(s, NewsResponse[].class);
            new Delete().from(News.class).execute();
            for (NewsResponse news : response) {
                News existNews = new Select().from(News.class).where("code = ? ", news.code).executeSingle();
                if (existNews == null) {
                    News newNews = new News();
                    newNews.bodyText = news.BodyText;
                    newNews.title = news.Title;
                    newNews.important = news.Important;
                    newNews.newsDate = news.NewsDate;
                    newNews.newsID = news.code;
                    newNews.save();
                } else {
                    existNews.bodyText = news.BodyText;
                    existNews.title = news.Title;
                    existNews.important = news.Important;
                    existNews.newsDate = news.NewsDate;
                    existNews.save();
                }
            }
            EventBus.getDefault().post(new EventOnGetNewsResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getNewsResponse() " + e.getMessage());
        }
    }

    public static void getAlertResponse(String json) {
        Logger.d("JsonParser : getAlertResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Message");
            EventBus.getDefault().post(new EventOnGetAlertResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getAlertResponse() " + e.getMessage());
        }
    }

    public static void getNotifiesResponse(String json, boolean showNotification) {
        Logger.d("JsonParser : getNotifiesResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("Notifies");
            String s = jsonArray.toString();
            NotifyResponse[] response = gson.fromJson(s, NotifyResponse[].class);
            int countNewNotify = 0;
            List<Notify> notifies = new Select().from(Notify.class).execute();
            if (response.length < 1) {
                new Delete().from(Notify.class).execute();

            }
            for (NotifyResponse notify : response) {
                Notify existNotify = new Select().from(Notify.class).where("NotifyCode = ? ", notify.Code).executeSingle();
                boolean a = false;
                for (Notify notify1 : notifies) {
                    if (notify1.notifyCode == notify.Code)
                        a = true;
                }
//                if (!a) {
//                    new Delete().from(Notify.class).where("NotifyCode = ? ", notify.Code).execute();
//                }


//                if (existNotify == null) {
                    Notify newNotify = new Notify();
                    newNotify.notifyCode = notify.Code;
                    newNotify.message = notify.Body;
                    newNotify.Title = notify.Title;
                    newNotify.setDate(notify.Date);
                    newNotify.isSeen = false;
                    newNotify.save();
                    countNewNotify++;
//                } else {
//                    existNotify.notifyCode = notify.Code;
//                    existNotify.Title = notify.Title;
//                    existNotify.message = notify.Body;
//                    existNotify.setDate(notify.Date);
//                    existNotify.save();
//                }
            }
            EventBus.getDefault().post(new EventOnGetNotifiesResponse(Arrays.asList(response)));
//            if (countNewNotify > 0 && showNotification) {
//                if (countNewNotify == 1) {
//                    ShortcutBadger.applyCount(G.context, 1); //for 1.1.4+
//                    List<Notify> unSeenNotify = new Select()
//                            .from(Notify.class)
//                            .where("IsSeen = ?", false)
//                            .execute();
//                    int countUnSeenNotify = unSeenNotify.size();
//                    if (countUnSeenNotify != 0) {
//                        ShortcutBadger.applyCountOrThrow(G.context, countUnSeenNotify); //for 1.1.4+
//                    }
//                    U.sendNotification(response[response.length - 1].Body);
//                } else {
//                    List<Notify> unSeenNotify = new Select()
//                            .from(Notify.class)
//                            .where("IsSeen = ?", false)
//                            .execute();
//                    int countUnSeenNotify = unSeenNotify.size();
//                    if (countUnSeenNotify != 0) {
//                        ShortcutBadger.applyCountOrThrow(G.context, countUnSeenNotify); //for 1.1.4+
//                    }
//                    U.sendNotification("" + countNewNotify + " پیغام جدید");
//                }
//            }
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getNotifiesResponse() " + e.getMessage());
        }
    }

    public static void getPollResponse(String json) {
        Logger.d("JsonParser : getPollResponse json is  " + json);
        try {
            GetPollResponse response = gson.fromJson(json, GetPollResponse.class);
            if (response.Result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }

            //agar 0 bashad yani nazar sanji nadarad
            if (response.getCode() != 0) {
                EventBus.getDefault().post(new EventOnGetPollResponse(response));
            }
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPollResponse() " + e.getMessage());
        }
    }

    public static void setPollResponse(String json) {
        Logger.d("JsonParser : setPollResponse json is  " + json);
        try {

            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            String message = jsonObject.getString("Message");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            EventBus.getDefault().post(new EventOnSetPollResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on setPollResponse() " + e.getMessage());
        }
    }

    public static void GetAdvsResponse(String json) {
        Logger.d("JsonParser : GetAdvsResponse json is  " + json);
        try {
            GetAdvsResponse[] response = gson.fromJson(json, GetAdvsResponse[].class);
            EventBus.getDefault().post(new EventOnGetAdvsResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on GetAdvsResponse() " + e.getMessage());
        }
    }

    public static void setAdsRepResponse(String json) {
        Logger.d("JsonParser : setAdsRepResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            EventBus.getDefault().post(new EventOnSetAdsRepResponse(status));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on setAdsRepResponse() " + e.getMessage());
        }
    }

    public static void GetIspInfoResponse(String json) {
        Logger.d("JsonParser : GetIspInfoResponse json is  " + json);
        try {
            GetIspInfoResponse[] response = gson.fromJson(json, GetIspInfoResponse[].class);
            EventBus.getDefault().post(new EventOnGetIspInfoResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on GetIspInfoResponse() " + e.getMessage());
        }
    }

    public static void visitMobileResponse(String json) {
        Logger.d("JsonParser : VisitMobileResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            EventBus.getDefault().post(new EventOnGetVisitMobileResponse(status));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on VisitMobileResponse() " + e.getMessage());
        }
    }

    public static void getUpdateResponse(String json) {
        Logger.d("JsonParser : getUpdateResponse json is  " + json);
        try {
            getUpdate response = gson.fromJson(json, getUpdate.class);
            EventBus.getDefault().post(new EventOnGetUpdateResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getUpdateResponse() " + e.getMessage());
        }
    }

    public static void getChargeOnlineMainItemResponse(String json) {
        Logger.d("JsonParser : getChargeOnlineMainItemResponse json is  " + json);
        try {
            final ChargeOnlineMainItemResponse response = gson.fromJson(json, ChargeOnlineMainItemResponse.class);
            if (response.Result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            } else if (response.Result != EnumResponse.OK) {
                final DialogClass dialogClass = new DialogClass();
                G.currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogClass.showMessageDialog("خطا", response.Message);
                    }
                });
            }
            EventBus.getDefault().post(new EventOnGetChargeOnlineMainItem(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineMainItemResponse() " + e.getMessage());
        }
    }

    public static void getCheckChargeOnlineClub(String json, int whichMenuItem) {
        Logger.d("JsonParser : getCheckChargeOnlineClub json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            boolean isClub = jsonObject.getBoolean("Club");
            if (status != EnumResponse.OK) {
                G.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DialogClass dlg = new DialogClass();
                        dlg.showMessageDialog("خطا", "" + "خطا در دریافت اطلاعات اکانتینگ شما رخ داده است لطفا مجددا چک کنید");
                    }
                });
            }
            EventBus.getDefault().post(new EventOnGetCheckChargeOnlineClub(status, isClub, whichMenuItem));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChangePasswordResponse() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForTamdid(String json) {
        Logger.d("JsonParser : getChargeOnlineForTamdid json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("Result");
            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            String message = jsonObject.getString("Message");
            long factorCode = jsonObject.getLong("FactorCode");
//            if(!status)  {
//                G.handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        G.dialog.showMessageDialog("" +  "خطا در دریافت اطلاعات اکانتینگ شما رخ داده است لطفا مجددا چک کنید");
//                    }
//                });
//            }
            EventBus.getDefault().post(new EventOnGetChargeOnlineForTamdid(result, message, factorCode));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChangePasswordResponse() " + e.getMessage());
        }

    }

    public static void getSelectFactorResponse(String json) {
        Logger.d("JsonParser : getSelectFactorResponse json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("FactorItems");
            String s = jsonArray.toString();
            SelectFactorResponse[] response = gson.fromJson(s, SelectFactorResponse[].class);
            EventBus.getDefault().post(new EventOnGetSelectFactorResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getSelectFactorResponse() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForLoadGroups(String json) {
        Logger.d("JsonParser : getChargeOnlineForLoadGroups json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("groups");
            String s = jsonArray.toString();
            ChargeOnlineGroup[] response = gson.fromJson(s, ChargeOnlineGroup[].class);
            EventBus.getDefault().post(new EventOnGetChargeOnlineForLoadGroups(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForLoadGroups() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForLoadPackages(String json) {
        Logger.d("JsonParser : getChargeOnlineForLoadPackages json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("packages");
            String s = jsonArray.toString();
            ChargeOnlineGroupPackage[] response = gson.fromJson(s, ChargeOnlineGroupPackage[].class);
            EventBus.getDefault().post(new EventOnGetChargeOnlineForLoadPackages(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForLoadPackages() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForLoadCategory(String json) {
        Logger.d("JsonParser : getChargeOnlineForLoadCategory json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("Categories");
            String s = jsonArray.toString();
            ChargeOnlineCategory[] response = gson.fromJson(s, ChargeOnlineCategory[].class);
            EventBus.getDefault().post(new EventOnGetChargeOnlineForLoadCategory(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForLoadCategory() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForCountCategory(String json, int isClub, long groupCode, int whichMenuItem) {
        Logger.d("JsonParser : getChargeOnlineForLoadCategory json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("Result");
            if (status == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("Categories");
            String s = jsonArray.toString();
            ChargeOnlineCategory[] response = gson.fromJson(s, ChargeOnlineCategory[].class);
            EventBus.getDefault().post(new EventOnGetChargeOnlineForCountCategoryResponse((Arrays.asList(response)), isClub, whichMenuItem, groupCode));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForLoadCategory() " + e.getMessage());
        }
    }


    public static void getChargeOnlineForSelectPackage(String json) {
        Logger.d("JsonParser : getChargeOnlineForSelectPackage json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("Result");

            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            String message = jsonObject.getString("Message");
            long factorCode = jsonObject.getLong("FactorCode");
//            if(!status)  {
//                G.handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        G.dialog.showMessageDialog("" +  "خطا در دریافت اطلاعات اکانتینگ شما رخ داده است لطفا مجددا چک کنید");
//                    }
//                });
//            }
            EventBus.getDefault().post(new EventOnGetChargeOnlineForSelectPackage(result, message, factorCode));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForSelectPackage() " + e.getMessage());
        }
    }

    public static void getIspUrlResponse(String json) {
        Logger.d("JsonParser : getIspUrlResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String name = jsonObject.getString("Name");
            String url = jsonObject.getString("MyLink");
            if (url.length() != 0) {
                G.currentUser.ispUrl = url;
                if (!G.currentUser.Token.equals("")) {
                    /** dar surati ke shakhs login karde bashad etelaate aan ra beruz mikonim.*/
                    G.currentUser.save();
                }
            }
            EventBus.getDefault().post(new EventOnGetIspUrlResponse(G.currentUser.ispUrl));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getIspUrlResponse() " + e.getMessage());
        }
    }

    public static void getPayOnlineForPayRequest(String json) {
        Logger.d("JsonParser : getPayOnlineForPayRequest json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            long orderId = jsonObject.getLong("OrderId");
            String orderUssd = jsonObject.getString("OrderId2");
            EventBus.getDefault().post(new EventOnGetPayOnlineForPayResponse(orderId, orderUssd));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPayOnlineForPayRequest() " + e.getMessage());
        }
    }

    public static void getCheckOrderIdResultRequest(String json) {
        Logger.d("JsonParser : getCheckOrderIdResultRequest json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean result = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Message");
            long factorCode = jsonObject.getLong("FactorCode");
            EventBus.getDefault().post(new EventOnGetCheckOrderIdResultResponse(result, message, factorCode));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCheckOrderIdResultRequest() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForPayRequest(String json) {
        Logger.d("JsonParser : getChargeOnlineForPayRequest json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            long orderId = jsonObject.getLong("OrderId");
            String orderUssd = jsonObject.getString("OrderId2");
            EventBus.getDefault().post(new EventOnGetChargeOnlineForPayResponse(orderId, orderUssd));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForPayRequest() " + e.getMessage());
        }
    }

    public static void getCityResponse(String json) {
        Logger.d("JsonParser : getCityResponse json is  " + json);
        try {
            CityResponse[] response = gson.fromJson(json, CityResponse[].class);
            EventBus.getDefault().post(new EventOnGetCityResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCityResponse() " + e.getMessage());
        }
    }

    public static void getCityGroupsResponse(String json) {
        Logger.d("JsonParser : getCityGroupsResponse json is  " + json);
        try {
            CityGroupResponse[] response = gson.fromJson(json, CityGroupResponse[].class);
            EventBus.getDefault().post(new EventOnGetCityGroupsResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCityGroupsResponse() " + e.getMessage());
        }
    }

    public static void getRegisterCustomerResponse(String json) {
        Logger.d("JsonParser : getRegisterCustomerResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean result = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Message");
            EventBus.getDefault().post(new EventOnGetRegisterCustomerResponse(result, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getRegisterCustomerResponse() " + e.getMessage());
        }
    }

    public static void getBankListRequest(String json) {
        Logger.d("JsonParser : getBankListRequest json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("Result");

            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("banks");
            String s = jsonArray.toString();
            LoadBanksResponse[] response = gson.fromJson(s, LoadBanksResponse[].class);
//            /** delete all item in FactorDetail table and save new FactorDetail on it.*/
            //U.deleteFactorDetailTableItem(factorCode);
            EventBus.getDefault().post(new EventOnGetBankListResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getBankListRequest() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorGetBankList(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
        }
    }

    public static void getCheckTarazRequest(String json) {
        Logger.d("JsonParser : getCheckTarazRequest json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("Result");

            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            int taraz = jsonObject.getInt("Taraz");
            boolean CanPayment = jsonObject.getBoolean("CanPayment");
            EventBus.getDefault().post(new EventOnGetCheckTarazResponse(taraz, CanPayment));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCheckTarazRequest() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorCheckTaraz(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
        }
    }

    public static void getCallBankPageRequest(String json) {
        Logger.d("JsonParser : getCallBankPageRequest json is  " + json);
        try {
            EventBus.getDefault().post(new EventOnGetBankPageResponse(json));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCallBankPageRequest() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorCallBankPage(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
        }
    }

    public static void getPayFactorFromCreditRequest(String json) {
        Logger.d("JsonParser : getPayFactorFromCreditRequest json is  " + json);
        try {

            PayFactorFromCreditResponse response = gson.fromJson(json, PayFactorFromCreditResponse.class);
            EventBus.getDefault().post(new EventOnGetPayFactorFromCreditResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPayFactorFromCreditRequest() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorPayFactorFromCredit(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
        }
    }

    public static void getLocations(String json) {
        Logger.d("JsonParser : getLocations json is  " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("Result");

            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("points");
            String s = jsonArray.toString();
            final LocationsResponse[] response = gson.fromJson(s, LocationsResponse[].class);
            U.deleteLocationsItem();
            G.GpsPoints = new ArrayList<>();

            for (LocationsResponse aResponse : response) {
                Locations locations = new Locations();
                locations.setScoreInMonth(aResponse.month);
                locations.setStartDate(aResponse.startDate);
                locations.setShowInOffline(false);
                locations.setScoreTypeCode(aResponse.scoreTypeCode);
                locations.setLatitude(aResponse.positionX);
                locations.setLongitude(aResponse.positionY);
                locations.setLastDate("0000");
                locations.setScoreInDay(aResponse.day);
                locations.setCode(aResponse.code);
                locations.setDes(aResponse.des);
                locations.setScore(aResponse.score);
                locations.setName(aResponse.name);
                G.GpsPoints.add(locations);

                //dar db save mikonim
                locations.save();

//                        Locations l = new Select().from(Locations.class).where("code = ?", response[i].code).executeSingle();
//                        if (l == null) {
//                            ActiveAndroid.beginTransaction();
//                            Locations locations = new Locations();
//                            locations.setCode(response[i].code);
//                            locations.setLatitude(response[i].positionX);
//                            locations.setDes(response[i].Name);
//                            locations.setLongitude(response[i].positionY);
//                            locations.setStartDate(response[i].startDate);
//                            locations.setEndDate(response[i].endDate);
//                            locations.setScoreTypeCode(response[i].scoreTypeCode);
//                            locations.setHasConditions(false);
//                            locations.setShowInOffline(false);
//                            locations.setScoreInDay(response[i].day);
//                            locations.setScoreInMonth(response[i].month);
//                            locations.save();
//                            ActiveAndroid.setTransactionSuccessful();
//                            ActiveAndroid.endTransaction();
//                            G.updateLocations();
//                        } else if (l != null) {
////                            String update = "Latitude = " + response[i].positionX +
////                                    ",Des = " + response[i].Name +
////                                    ",Longitude = " + response[i].positionY +
////                                    ",StartDate = " + response[i].startDate +
////                                    ",EndDate = " + response[i].endDate +
////                                    ",ScoreTypeCode = " + response[i].scoreTypeCode+
////                                    ",scoreInDay = " + response[i].day+
////                                    ",scoreInMonth = " + response[i].month;
//
//                            String up = "Latitude = ?,Des = ? ,Longitude = ? ,StartDate = ? ,EndDate = ?,ScoreTypeCode = ? ,scoreInDay = ?,scoreInMonth = ?";
//                            new Update(Locations.class)
//                                    .set(up, response[i].positionX, response[i].Name, response[i].positionY, response[i].startDate, response[i].endDate, response[i].scoreTypeCode, response[i].day, response[i].month)
//                                    .where("code = ?", G.locations.get(i).getCode())
//                                    .execute();
//                        }
//

            }
//                    EventBus.getDefault().post(new EventOnGetLocations());

            EventBus.getDefault().post(new EventOnGetLocations());


        } catch (Exception e) {
            Logger.e("JsonParser : Error on EventOnGetLocations() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetLocations());
        }

    }

    public static void addScoreResponse(String json, Locations location) {
        Logger.d("JsonParser : addScoreResponse json is  " + json);
        if (!json.equals("") && json != null) {
            AddScoreResponse addScoreResponses = gson.fromJson(json, AddScoreResponse.class);
            EventBus.getDefault().post(new EventOnAddScoreResponse(addScoreResponses, location));

        }//        1 sabt
//        0 ghbl
//       -1 tarikh

    }

    public static void getTicketCodesResponse(String json) {
        Logger.d("JsonParser : getTicketCodesResponse json is  " + json);
        try {
            if (!json.equals("") && json != null) {
                JSONObject jsonObject = new JSONObject(json);
                int result = jsonObject.getInt("Result");

                if (result == EnumResponse.ExkeyMistake) {
                    EventBus.getDefault().post(new EventOnExKeyMistake());
                    return;
                }
                JSONArray jsonArray = jsonObject.getJSONArray("ContactTitles");
                String s = jsonArray.toString();
                TicketCodeResponse[] ticketCodeResponse = gson.fromJson(s, TicketCodeResponse[].class);
                new Delete().from(TicketCodes.class).execute();
//                TicketCodes ticketCodes = new Select().from(TicketCodes.class).executeSingle();
//                new Delete().from(TicketCodes.class).execute();

                for (int i = 0; i < ticketCodeResponse.length; i++) {
                    TicketCodes ticketCodes = new TicketCodes();
                    ticketCodes.setName(ticketCodeResponse[i].name);
                    ticketCodes.setCode(ticketCodeResponse[i].code);
                    ticketCodes.setParent(ticketCodeResponse[i].parent);
                    ticketCodes.save();
                }

                EventBus.getDefault().post(new EventOnTicketCodeResponse());
            }
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getTicketCodesResponse() " + e.getMessage());
        }

    }

    public static void uploadImageResponse(String json) {
        Logger.d("JsonParser uploadImageResponse is " + json);
        try {
            JSONObject object = new JSONObject(json);
            int result = object.getInt("Result");

            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
//            String message = object.getString("Message");

            if (result == EnumResponse.OK) {
                EventBus.getDefault().post(new EventOnUploadImageResponse());
                WebService.getAvatarImage();
            } else {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            WebService.getAvatarImage();
        }


    }

    public static void getAvatarImage(String String) {
        Logger.d("JsonParser getAvatarImage is " + String);

        if (String != null && !String.equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(String);

                int result = jsonObject.getInt("Result");
                if (result == EnumResponse.ExkeyMistake) {
                    EventBus.getDefault().post(new EventOnExKeyMistake());
                    return;
                }

                //encoded ra dar db insert mikonim
                Info info = new Select().from(Info.class).executeSingle();
//                String encodedImage = jsonObject.getString("Base64Data");
                String Message = jsonObject.getString("Message");
                if (info == null) {
                    Info newInfo = new Info();
                    newInfo.Encoded64ImageString = Message;
                    newInfo.save();
                    G.currentUserInfo = newInfo;
                } else {
                    info.Encoded64ImageString = Message;
                    info.save();
                    G.currentUserInfo = info;
                }
                EventBus.getDefault().post(new EventOnGetAvatarImageResponse());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public static void getRepresenterURLRespone(String json) {
        Logger.d("JsonParser getRepresenterURLRespone is " + json);
//{"siteUrl":"","RepresenterURL":"/s85Atp","Message":null,"Result":4}
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("Result");
            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            String siteUrl = jsonObject.getString("siteUrl");
            String RepresenterURL = jsonObject.getString("RepresenterURL");

            EventBus.getDefault().post(new EventOnGetReperesenterURL(siteUrl, RepresenterURL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getCurrentTrafficSplite(String json) {
        Logger.d("JsonParser getCurrentTrafficSplite is " + json);

        CurrentTrraficSplite currentTrraficSplite = new GsonBuilder().create().fromJson(json, CurrentTrraficSplite.class);
        if (currentTrraficSplite.getResult() == EnumResponse.ExkeyMistake) {
            EventBus.getDefault().post(new EventOnExKeyMistake());
            return;
        }
        EventBus.getDefault().post(new EventOnGetCurrentTrafficSplite(currentTrraficSplite));

    }

    public static void getLoadTrafficSplitNotMain(String json) {
        //{"Packages":[{"Des":"","day":30,"package":"تست تقسیم","date":"1396/07/20 11:22","factorCode":11077,"code":658},{"Des":"","day":30,"package":"تست تقسیم","date":"1396/07/20 11:22","factorCode":11077,"code":659},{"Des":"","day":30,"package":"تست تقسیم","date":"1396/07/20 11:22","factorCode":11077,"code":660},{"Des":"","day":30,"package":"تست تقسیم","date":"1396/07/20 11:22","factorCode":11077,"code":661}],"Message":null,"Result":4}

        Logger.d("JsonParser getLoadTrafficSplitNotMain is " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("Result");
            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("Packages");
            String s = jsonArray.toString();
            TrafficSplitNotMainModel[] currentTrraficSplite = new GsonBuilder().create().fromJson(s, TrafficSplitNotMainModel[].class);
            EventBus.getDefault().post(new EventOnLoadTrafficSplitNotMain(currentTrraficSplite));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void getRegionsResponse(String json) {
        Logger.d("JsonParser getRegionsResponse is " + json);
        List<ModelRegions> modelRegionses = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("regions");
            int result = jsonObject.getInt("Result");
            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                ModelRegions modelRegions = new ModelRegions(o.getInt("code"), o.getString("name"), o.getInt("preTel"));
                modelRegionses.add(modelRegions);
            }

            EventBus.getDefault().post(new EventOnSuccessGetRegions(modelRegionses));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void getCitiesResponse(String json) {
        Logger.d("JsonParser getCitiesResponse is " + json);

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("cities");
            int result = jsonObject.getInt("Result");
            if (result == EnumResponse.ExkeyMistake) {
                EventBus.getDefault().post(new EventOnExKeyMistake());
                return;
            }
            String s = jsonArray.toString();
            ModelCities[] modelCities = new GsonBuilder().create().fromJson(s, ModelCities[].class);
            EventBus.getDefault().post(new EventOnSuccessGetCities(Arrays.asList(modelCities)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void registerCustomerResponse(String json) {
        Logger.d("JsonParser registerCustomerResponse is " + json);
        ModelRegisterCustomerResponse response = G.gson.fromJson(json, ModelRegisterCustomerResponse.class);
        if (response.getResult() == EnumResponse.ExkeyMistake) {
            EventBus.getDefault().post(new EventOnExKeyMistake());
            return;
        }
        EventBus.getDefault().post(new EventOnSuccessRegisterCustomer(response));
    }

    public static void GetCountNotifyResponse(String json) {
        Logger.d("JsonParser GetCountNotifyResponse is " + json);

        try {
            JSONObject jsonObject = new JSONObject(json);
            int Count = jsonObject.getInt("Count");
            EventBus.getDefault().post(new EventOnGetCountNotify(Count));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
