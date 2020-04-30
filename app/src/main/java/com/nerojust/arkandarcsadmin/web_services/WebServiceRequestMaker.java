package com.nerojust.arkandarcsadmin.web_services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.nerojust.arkandarcsadmin.models.login.LoginResponse;
import com.nerojust.arkandarcsadmin.models.login.LoginSendObject;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.models.products.ProductsSendObject;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductResponse;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductsSendObject;
import com.nerojust.arkandarcsadmin.models.registration.RegistrationResponse;
import com.nerojust.arkandarcsadmin.models.registration.RegistrationSendObject;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.utils.MyApplication;
import com.nerojust.arkandarcsadmin.utils.SessionManager;
import com.nerojust.arkandarcsadmin.web_services.interfaces.AddProductInterface;
import com.nerojust.arkandarcsadmin.web_services.interfaces.LoginInterface;
import com.nerojust.arkandarcsadmin.web_services.interfaces.ProductInterface;
import com.nerojust.arkandarcsadmin.web_services.interfaces.RegisterInterface;
import com.nerojust.arkandarcsadmin.web_services.interfaces.UpdateProductInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WebServiceRequestMaker {
    private final String TAG = "WebserviceRequestMaker";
    private final Retrofit retrofit = MyApplication.getInstance().getRetrofit();
    private final PostInterface postInterfaceService = retrofit.create(PostInterface.class);
    private final GetInterface getInterface = retrofit.create(GetInterface.class);
    private SessionManager sessionManager = AppUtils.getSessionManagerInstance();


    public void loginInUser(LoginSendObject customerLoginSendObject, LoginInterface loginResponseInterface) {
        Call<LoginResponse> call = postInterfaceService.loginUser(customerLoginSendObject);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();

                    if (loginResponse != null) {
                        if (loginResponse.isSuccessful()) {
                            loginResponseInterface.onSuccess(loginResponse);
                        } else {
                            loginResponseInterface.onError(loginResponse.getMessage());
                        }
                    }
                } else {
                    loginResponseInterface.onError(String.valueOf(response.code()));
                    Log.d(TAG, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                if (t.getMessage() != null) {
                    if (Objects.requireNonNull(t.getMessage()).contains("failed to connect")) {
                        loginResponseInterface.onError("Network Error, please try again");
                    } else {
                        loginResponseInterface.onError(t.getMessage());
                    }
                    String error = (t.getMessage() == null) ? "No error message" : t.getMessage();
                    Log.e("Login error", error);
                } else {
                    loginResponseInterface.onError("Network error");
                }
            }
        });
    }

    public void registerUser(RegistrationSendObject registrationSendObject, RegisterInterface registerInterface) {
        Call<RegistrationResponse> call = postInterfaceService.createNewUser(registrationSendObject);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResponse> call, @NonNull Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();

                    if (registrationResponse != null) {
                        if (registrationResponse.isSuccessful()) {
                            registerInterface.onSuccess(registrationResponse);
                        } else {
                            registerInterface.onError(registrationResponse.getMessage());
                        }
                    }
                } else {
                    registerInterface.onError(String.valueOf(response.code()));
                    Log.d(TAG, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResponse> call, @NonNull Throwable t) {
                if (t.getMessage() != null) {
                    if (Objects.requireNonNull(t.getMessage()).contains("failed to connect")) {
                        registerInterface.onError("Network Error, please try again");
                    } else {
                        registerInterface.onError(t.getMessage());
                    }
                    String error = (t.getMessage() == null) ? "No error message" : t.getMessage();
                    Log.e("Login error", error);
                } else {
                    registerInterface.onError("Network error");
                }
            }
        });
    }

    public void getAllProducts(ProductInterface productInterface) {
        Call<ProductsResponse> call = getInterface.getAllProducts();
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (response.isSuccessful()) {
                    ProductsResponse products = response.body();
                    productInterface.onSuccess(products);
                } else {
                    productInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                if (t.getMessage() != null) {
                    if (Objects.requireNonNull(t.getMessage()).contains("failed to connect")) {
                        productInterface.onError("Network Error, please try again");
                    } else {
                        productInterface.onError(t.getMessage());
                    }
                    String error = (t.getMessage() == null) ? "No error message" : t.getMessage();
                    Log.e("Login error", error);
                } else {
                    productInterface.onError("Network error");
                }
            }
        });
    }

    public void editOneProduct(UpdateProductsSendObject updateProductsSendObject, UpdateProductInterface productInterface) {
        Call<UpdateProductResponse> call = getInterface.editOneProduct(updateProductsSendObject, sessionManager.getProductId());
        call.enqueue(new Callback<UpdateProductResponse>() {
            @Override
            public void onResponse(Call<UpdateProductResponse> call, Response<UpdateProductResponse> response) {
                if (response.isSuccessful()) {
                    UpdateProductResponse products = response.body();
                    productInterface.onSuccess(products);
                } else {
                    productInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<UpdateProductResponse> call, Throwable t) {
                if (t.getMessage() != null) {
                    if (Objects.requireNonNull(t.getMessage()).contains("failed to connect")) {
                        productInterface.onError("Network Error, please try again");
                    } else {
                        productInterface.onError(t.getMessage());
                    }
                    String error = (t.getMessage() == null) ? "No error message" : t.getMessage();
                    Log.e("Login error", error);
                } else {
                    productInterface.onError("Network error");
                }
            }
        });
    }
    public void addNewProduct(ProductsSendObject productsSendObject, AddProductInterface addProductInterface) {
        Call<ProductsResponse> call = postInterfaceService.addNewProduct(productsSendObject);
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (response.isSuccessful()) {
                    ProductsResponse products = response.body();
                    addProductInterface.onSuccess(products);
                } else {
                    addProductInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                if (t.getMessage() != null) {
                    if (Objects.requireNonNull(t.getMessage()).contains("failed to connect")) {
                        addProductInterface.onError("Network Error, please try again");
                    } else {
                        addProductInterface.onError(t.getMessage());
                    }
                    String error = (t.getMessage() == null) ? "No error message" : t.getMessage();
                    Log.e("Login error", error);
                } else {
                    addProductInterface.onError("Network error");
                }
            }
        });
    }
}
