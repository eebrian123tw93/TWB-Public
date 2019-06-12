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


class ArticleListVC: TWBViewController ,UITableViewDelegate,UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return articles.count
    }
    
    @IBOutlet weak var tableView: UITableView!
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let articleCell=Bundle.main.loadNibNamed("ArticleCell", owner: self, options: nil)?.first as! ArticleCell
        let article=articles[indexPath.row]
        articleCell.title.text=article.title
        articleCell.userId.text=article.userId
        articleCell.preview.text=article.content
        articleCell.likeCount.text=String(describing: article.points)
        articleCell.views.text=String(describing: article.views)
        articleCell.commentCount.text=String(describing: article.commentCount)
        if article.images.count == 0 {
            
            articleCell.myImage.isHidden=true
        }else{
            articleCell.myImage.isHidden=false
            let url = URL(string:article.images.first!)
            articleCell.myImage.kf.setImage(with: url)
        
        }
        return articleCell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 137;
    }
    
    
    
//    @IBOutlet weak var collectview: UICollectionView!
    var articles:[Article]=[]
   

    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.tableView.delegate=self
        self.tableView.dataSource=self
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
                            self.tableView.reloadData()
                            
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
