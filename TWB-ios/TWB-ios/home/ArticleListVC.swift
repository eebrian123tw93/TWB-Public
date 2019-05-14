//
//  ArticleListVC.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/13.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa
import Kingfisher


class ArticleListVC: TWBViewController ,UICollectionViewDelegate,UICollectionViewDataSource {
    
    @IBOutlet weak var collectview: UICollectionView!
    var articles:[Article]=[]
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return articles.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let articleCell = collectionView.dequeueReusableCell(withReuseIdentifier: "ArticleCell", for: indexPath)as! ArticleCell
        let article=articles[indexPath.row]
        articleCell.titleLabel.text=article.title
        articleCell.userIdLabel.text=article.userId
        articleCell.contentLabel.text=article.content
        if article.images.count == 0 {
            
            articleCell.imageView.isHidden=true
        }else{
            articleCell.imageView.isHidden=false
            let url = URL(string:article.images.first!)
            articleCell.imageView.kf.setImage(with: url)
//            articleCell.imageView.kf
//
//            let data = try? Data(contentsOf: url!) //make sure your image in this url does exist, otherwise unwrap in a if let check / try-catch
////            imageView.image =
//            
//            articleCell.imageView.image=UIImage(data: data!)?.resized(width: 100, height: 100)
        }
        return articleCell
    }
    

    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.collectview.delegate=self
        self.collectview.dataSource=self
        let user=User(userId: "3nxn", password: "3nxn")
        let endTime = Date()
        let startTime:Date = Calendar.current.date(byAdding: .day, value: -30, to: Date()) ?? endTime
        let observer: AnyObserver<(HTTPURLResponse,Data)> = AnyObserver { [unowned self] (event) in
            switch event {
                
            case .next((let response,let data)):
                if 200 ..< 300 ~= response.statusCode {
                    let str = String(data: data, encoding: String.Encoding.utf8)
                    print("登入成功", str ?? "")
                    
                    do{
                            let decoder:JSONDecoder=JSONDecoder()
                            let dateFormatter = DateFormatter()
                            dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
                            decoder.dateDecodingStrategy = .formatted(dateFormatter)
                        
                            let articles=try decoder.decode([Article].self, from: (str?.data(using: .utf8))!)
                            print(articles.count)
                            articles.forEach({ (article) in
                            self.articles.append(article)
                            self.collectview.reloadData()
                            
                        })
                            
                        
                    }catch is Error{
                        
                    }
                    
                }else{
//                    let str = String(data: data, encoding: String.Encoding.utf8)
//
//                    print("登入失敗", str ?? "")
                }
                break
            case .error(let error):
                
                break
            default:
                break
            }
        }
        
        ShuoApiService.instance.getArticles(user: user, orderBy: "create_time", startTime: startTime, endTime: endTime, limit: 50, offset: 0).subscribe(observer).disposed(by: disposeBag)
        
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
