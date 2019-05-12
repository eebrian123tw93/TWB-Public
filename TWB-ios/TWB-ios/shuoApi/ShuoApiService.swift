//
//  ShuoApiService.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/11.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import RxAlamofire
import RxSwift
import Alamofire
import SwiftyJSON

class ShuoApiService {
    
    static let instance = ShuoApiService()
    
    private var baseURL="http://34.80.143.220:9999/shuo"
    let disposeBag = DisposeBag()
    private  init() {
    }

    
    
    func login(user:User)-> Observable<(HTTPURLResponse,Data)>{
        let  path = "/checkUserExist"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.get, url, parameters: nil, headers: ["Authorization":user.authKey()])
    }
    func register(user:User) -> Observable<(HTTPURLResponse,Data)>  {
         let  path = "/public/register"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.post, url, parameters:user.toJson()
            ,encoding: JSONEncoding.default, headers: ["Content-Type":"application/json"])
    }
    
    func deleteUser(user:User) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/deleteUser/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.delete, url, parameters: nil, headers: ["Authorization":user.authKey()])
    }

    func forgotPassword(email:String) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/public/forgotPassword/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.get, url, parameters: ["email":email])
    }
    
  
    
//    @Headers("Content-Type:application/json")
//    @POST("/shuo/postArticle/")
//    Observable<Response<ResponseBody>> postArticle(@Header("Authorization") String authKey, @Body String s);
    
//
//    @POST("/shuo/public/viewed")
//    Observable<Response<ResponseBody>> viewed(@Body String articleId);
//
//
//    @Headers("Content-Type:application/json")
//    @POST("/shuo/like/")
//    Observable<Response<ResponseBody>> like(@Header("Authorization") String authKey, @Body String s);
//
//    @Headers("Content-Type:application/json")
//    @POST("/shuo/comment/")
//    Observable<Response<ResponseBody>> comment(@Header("Authorization") String authKey, @Body String s);
//
//    @GET("/shuo/public/getComments/")
//    @Deprecated
//    Observable<Response<JsonArray>> getComments(@Query("articleId") String articleId);
//
//
//    @GET("/shuo/public/getArticleData/")
//    Observable<Response<JsonObject>> getArticleDataPublic(@Query("articleId") String articleId);
//
//    @GET("/shuo/getArticleData/")
//    Observable<Response<JsonObject>> getArticleDataPrivate(@Header("Authorization") String authKey, @Query("articleId") String articleId);
//
//
//    @GET("/shuo/public/searchArticle/")
//    Observable<Response<JsonArray>> searchArticle(@Query("keyWord") String keyWord, @Query("limit") int limit, @Query("offset") int offset);

//
//    @Deprecated
//    @GET("/shuo/getArticles/")
//    Observable<Response<JsonArray>> getArticlesPrivate(@Header("Authorization") String authKey, @Query("endTime") LocalDateTime endDateTime, @Query("startTime") LocalDateTime startGateTime, @Query("orderBy") String orderBy, @Query("offset") int offset, @Query("limit") int limit);
//
//    @GET("/shuo/public/getArticles/")
//    Observable<Response<JsonArray>> getArticles(@Header("Authorization") String authKey, @Query("endTime") LocalDateTime endDateTime, @Query("startTime") LocalDateTime startGateTime, @Query("orderBy") String orderBy, @Query("offset") int offset, @Query("limit") int limit);
//
//
//    @GET("/shuo/public/getUserPostHistory/")
//    Observable<Response<JsonArray>> getUserPostHistory(@Header("Authorization") String authKey, @Query("authorId") String authorId);

    
    
    func test(observer:AnyObserver<Data>)  {
        let urlString = "https://www.douban.com/j/app/radio/channels"
        let url = URL(string:urlString)!
        request(.get, url)
            .data()
            .subscribe(observer).disposed(by: disposeBag)
        
    }
}
