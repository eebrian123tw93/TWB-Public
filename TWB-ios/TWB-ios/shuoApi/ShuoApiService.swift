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
    private  init() {
    }

    
    let disposeBag = DisposeBag()
    func test(observer:AnyObserver<Data>)  {
        
        
        let urlString = "https://www.douban.com/j/app/radio/channels"
        let url = URL(string:urlString)!
        
        request(.get, url)
            .data()
            .subscribe(observer).disposed(by: disposeBag)
        
    }

}
