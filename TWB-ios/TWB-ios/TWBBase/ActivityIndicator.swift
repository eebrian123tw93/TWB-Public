//
//  ActivityIndicator.swift
//  RxExample
//
//  Created by Krunoslav Zaher on 10/18/15.
//  Copyright © 2015 Krunoslav Zaher. All rights reserved.
//
/**
 這是從 RxSwift 的範例裡拿的
 因為功能方便，剛好也需要，就直接複製過來用
 用來通知 api 是否完成，讓 view 可以決定要不要顯示 loading hud
 
 用法
 
 // 在 viewModel 生成一個 ActivityIndicator instance
 let loginIndicator = ActivityIndicator()
 //然後對想要監視 observable 執行
 let doLogin: Observable<Bool> = API.login().trackActivity(loginIndicator)
 
 然後把 doLogin 回傳給 VC，doLogin 它所代表的就是 login api 是否正在執行
 VC 就可以 subscribe 收到 true 就顯示 loading, false 就隱藏 loading
 
 */
import RxSwift
import RxCocoa

private struct ActivityToken<E> : ObservableConvertibleType, Disposable {
    private let _source: Observable<E>
    private let _dispose: Cancelable
    
    init(source: Observable<E>, disposeAction: @escaping () -> Void) {
        _source = source
        _dispose = Disposables.create(with: disposeAction)
    }
    
    // Disposable
    func dispose() {
        _dispose.dispose()
    }
    
    // ObservableConvertibleType
    func asObservable() -> Observable<E> {
        return _source
    }
}

/**
 Enables monitoring of sequence computation.
 
 If there is at least one sequence computation in progress, `true` will be sent.
 When all activities complete `false` will be sent.
 */
public class ActivityIndicator : SharedSequenceConvertibleType {
    public typealias E = Bool
    public typealias SharingStrategy = DriverSharingStrategy
    
    private let _lock = NSRecursiveLock()
    private let _activityCount = BehaviorRelay(value: 0)
    private let _loading: SharedSequence<SharingStrategy, Bool>
    
    public init() {
        _loading = _activityCount.asDriver()
            .map { $0 > 0 }
            .distinctUntilChanged()
    }
    // 看起來是可以監聽多個動作，當每個動作都完成後，才回傳 false
    // using 創建一個會隨 observable 生命週期結束的物件，下面例子
    // ActivityToken 就是 disposable resource
    fileprivate func trackActivityOfObservable<O: ObservableConvertibleType>(_ source: O) -> Observable<O.E> {
        return Observable.using({ () -> ActivityToken<O.E> in
            // 附帶的資源
            self.increment()
            // ActivityToken 的寫法，就是把 source 再包一層，當 source dispose 的時候，會執行 self.decrement
            return ActivityToken(source: source.asObservable(), disposeAction: self.decrement)
        }) { token in
            // 實際回傳的 observable， token 就是 ActivityToken
            return token.asObservable() // return source
        }
    }
    
    public func increment() {
        _lock.lock()
        _activityCount.accept(_activityCount.value + 1)
        _lock.unlock()
    }
    
    public func decrement() {
        _lock.lock()
        _activityCount.accept(_activityCount.value - 1)
        _lock.unlock()
    }
    
    public func asSharedSequence() -> SharedSequence<SharingStrategy, E> {
        return _loading
    }
}

protocol ActivityTrackable {
    func trackActivity(_ activityIndicator: ActivityIndicator)
}

// Observable 的 extension
extension ObservableConvertibleType {
    // 會呼叫這個的，是其它的 Observable，裡面做的是把這個 observable 傳入 activityIndicator 中
    public func trackActivity(_ activityIndicator: ActivityIndicator) -> Observable<E> {
        return activityIndicator.trackActivityOfObservable(self)
    }
}
