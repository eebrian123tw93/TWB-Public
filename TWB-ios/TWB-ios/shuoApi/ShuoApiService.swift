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
    
    
    
    func test(observer:AnyObserver<Data>)  {
        let urlString = "https://www.douban.com/j/app/radio/channels"
        let url = URL(string:urlString)!
        request(.get, url)
            .data()
            .subscribe(observer).disposed(by: disposeBag)
        
    }
}
