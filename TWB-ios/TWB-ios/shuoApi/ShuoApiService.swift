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
class ShuoApiService {
    
    static let instance = ShuoApiService()
    
    private var baseURL="http://34.80.143.220:9999/shuo"
    let disposeBag = DisposeBag()
    private  init() {
    }

    
    
    func login(user:User,observer:AnyObserver<(HTTPURLResponse,Data)>){
        let  path = "/checkUserExist"
        let url = URL(string:baseURL.appending(path))!
        requestData(.get, url, parameters: nil, headers: ["Authorization":user.authKey()])
            .subscribe(observer)
            .disposed(by: disposeBag)
    }
    func test(observer:AnyObserver<Data>)  {
        let urlString = "https://www.douban.com/j/app/radio/channels"
        let url = URL(string:urlString)!
        request(.get, url)
            .data()
            .subscribe(observer).disposed(by: disposeBag)
        
    }
}
