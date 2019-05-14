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
    
  
    func postArticle(user:User,article:Article) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/postArticle/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.post, url, parameters:article.toJson()
            ,encoding: JSONEncoding.default, headers: ["Content-Type":"application/json","Authorization":user.authKey()])
    }
    
//    @Headers("Content-Type:application/json")
//    @POST("/shuo/postArticle/")
//    Observable<Response<ResponseBody>> postArticle(@Header("Authorization") String authKey, @Body String s);
    
    
    //some wired
    func viewed(article:Article) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/public/viewed"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.post, url,encoding: article.articleId, headers: ["Content-Type":"application/json"])
    }
//
//    @POST("/shuo/public/viewed")
//    Observable<Response<ResponseBody>> viewed(@Body String articleId);
//
//
    
    
    func like(user:User,like:Like) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/like/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.post, url,parameters: like.toJson(),encoding: JSONEncoding.default, headers: ["Content-Type":"application/json","Authorization":user.authKey()])
    }
    
//    @Headers("Content-Type:application/json")
//    @POST("/shuo/like/")
//    Observable<Response<ResponseBody>> like(@Header("Authorization") String authKey, @Body String s);
//
    
    func comment(user:User,comment:Comment) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/comment/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.post, url,parameters: comment.toJson(),encoding: JSONEncoding.default, headers: ["Content-Type":"application/json","Authorization":user.authKey()])
    }
    
    
//    @Headers("Content-Type:application/json")
//    @POST("/shuo/comment/")
//    Observable<Response<ResponseBody>> comment(@Header("Authorization") String authKey, @Body String s);
    
    
    func getComments(article:Article) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/public/getComments/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.get, url,parameters: ["articleId":article.articleId],encoding: URLEncoding.default)
    }
    
//
//    @GET("/shuo/public/getComments/")
//    @Deprecated
//    Observable<Response<JsonArray>> getComments(@Query("articleId") String articleId);
//
    
    func getArticleDataPublic(article:Article) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/public/getArticleData/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.get, url,parameters: ["articleId":article.articleId],encoding: URLEncoding.default)
    }
    
//
//    @GET("/shuo/public/getArticleData/")
//    Observable<Response<JsonObject>> getArticleDataPublic(@Query("articleId") String articleId);
//
    func getArticleDataPrivate(user:User,article:Article) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/getArticleData/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.get, url,parameters: ["articleId":article.articleId],encoding: URLEncoding.default,headers: ["Authorization":user.authKey()])
    }
    
    
//    @GET("/shuo/getArticleData/")
//    Observable<Response<JsonObject>> getArticleDataPrivate(@Header("Authorization") String authKey, @Query("articleId") String articleId);
//
//
    func searchArticle(keyword:String,limit:Int,offset:Int) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/public/searchArticle/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.get, url,parameters: ["keyWord":keyword,"limit":limit,"offset":offset],encoding: URLEncoding.default)
    }
    
//    @GET("/shuo/public/searchArticle/")
//    Observable<Response<JsonArray>> searchArticle(@Query("keyWord") String keyWord, @Query("limit") int limit, @Query("offset") int offset);

    
    func getArticles(user:User,orderBy:String,startTime:Date,endTime:Date,limit:Int,offset:Int) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/public/getArticles/"
        //
        
        let dateFormatterGet = DateFormatter()
        dateFormatterGet.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"

        let url = URL(string:baseURL.appending(path))!
        return requestData(.get, url,parameters: ["startTime":dateFormatterGet.string(from: startTime),"endTime":dateFormatterGet.string(from: endTime),"orderBy":orderBy,"limit":limit,"offset":offset],encoding: URLEncoding.default,headers: ["Authorization":user.authKey()])
    }
    

//    @GET("/shuo/public/getArticles/")
//    Observable<Response<JsonArray>> getArticles(@Header("Authorization") String authKey, @Query("endTime") LocalDateTime endDateTime, @Query("startTime") LocalDateTime startGateTime, @Query("orderBy") String orderBy, @Query("offset") int offset, @Query("limit") int limit);
//
    func getUserPostHistory(user:User,article:Article) -> Observable<(HTTPURLResponse,Data)>  {
        let  path = "/public/getUserPostHistory/"
        let url = URL(string:baseURL.appending(path))!
        return requestData(.get, url,parameters: ["authorId":article.articleId],encoding: URLEncoding.default,headers: ["Authorization":user.authKey()])
    }
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







