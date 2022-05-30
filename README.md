## 工程结构
``` 
es-component

├── bean -- POJO类等
├    ├── config -- 配置相关POJO类等
├    ├── doc -- 操作doc相关POJO类等
├    ├    ├── query -- doc查询相关POJO类等
├    ├    └── write -- doc写相关POJO类等
├    ├── index -- 操作index相关POJO类等
├    └── response -- 返回相关相关POJO类等
├── builder -- 排序和查询条件构造器
├    ├── sort -- 排序条件构造器
├    ├── soure -- scoure查询构造，查询条件的集合构造
├    └── terms -- 查询条件构造器
├── component -- es工具入口
├── constant -- 全局常量
├── convert -- 全局解析转换器
├── enums -- 全局枚举
├── exception -- 全局自定义错误封装
├── interpreter -- 解释器，与外部api交互（解释器模式，如需使用其他外部api，可扩展相关解释器）
├    └── highlevel -- RestHighLevelClient api相关交互(默认default)
├         ├── client -- 外部api client
├         ├── convert -- 转换器（内部bean-> 外部bean）
├         ├── doc -- 操作doc相关解释器
├         └── index -- 操作index相关解释器
├── listener -- 监听器，用于异步回调
├── registry -- 注册器，使用时需要先执行注册器
└── service -- 内部api
     └── impl -- 内部api实现

```
## 使用说明
``` 
1. 使用前确保已经注册
例如：
 ElasticsearchConfig config = ElasticsearchConfig.builder()
  .ipAddress(new String[]{"192.168.188.57:9200","192.168.58.7:9200","192.168.188.59:9200"})
  .connectTimeOut(1000).socketTimeOut(30000).connectionRequestTimeOut(500)
  .maxConnectNum(100).maxConnectPerRoute(100).build(); 
 ElasticsearchRegistry.register(config);
``` 
## es返回code
``` 
- RestHighLevelClient 返回code 详见文件highLevelStatusCode.txt
``` 
## API 文档说明
### doc类
1. singleQuery 查询返回列表第一条数据
```
参数：
· String                indexName             索引名
· EsSearchSourceBuilder esSearchSourceBuilder es查询条件构造
· Class<T>              tClass                返回数据类型
```
```
返回：
· T
```
``` 
案例：
//创建查询条件
EsSearchSourceBuilder esSearchSourceBuilder = EsSearchSourceBuilder.create();
esSearchSourceBuilder.filter(EsQueryBuilders.termKeywordQuery("userId", "74ab79b44d3024e39bef0888d4ffef3f"))
            .offset(1).limit(10).timeOut(30L, TimeUnit.SECONDS);
//数查询
DelayLessAccessEntity delayLessAccessEntity = EsComponent.doc()
           .singleQuery("es-component-test", esSearchSourceBuilder, DelayLessAccessEntity.class);
``` 
2. query 查询
```
参数：
· String                indexName             索引名
· EsSearchSourceBuilder esSearchSourceBuilder es查询条件构造
· Class<T>              tClass                返回数据类型
```
```
返回：
· EsResponse<T>
```
``` 
案例：
EsSearchSourceBuilder esSearchSourceBuilder = EsSearchSourceBuilder.create();
esSearchSourceBuilder.filter(EsQueryBuilders.termKeywordQuery("userId", "74ab79b44d3024e39bef0888d4ffef3f"))
          .offset(1).limit(10).timeOut(30L, TimeUnit.SECONDS);
//数查询
EsResponse<DelayLessAccessEntity> esResponse = EsComponent.doc()
          .query("es-component-test", esSearchSourceBuilder, DelayLessAccessEntity.class);
```
3. queryMulti 批量查询
```
参数：
· String                            indexName              索引名
· Collection<EsSearchSourceBuilder> esSearchSourceBuilders es查询条件构造
· Class<T>                          tClass                 返回数据类型
```
```
返回：
· List<EsResponse<T>>
```
``` 
案例：
EsSearchSourceBuilder esSearchSourceBuilder = EsSearchSourceBuilder.create();
esSearchSourceBuilder.filter(EsQueryBuilders.termKeywordQuery("userId", "74ab79b44d3024e39bef0888d4ffef3f"))
          .offset(1).limit(10).timeOut(30L, TimeUnit.SECONDS);
Collection<EsSearchSourceBuilder> esSearchSourceBuilders = new ArraryList(1);
esSearchSourceBuilders.add(esSearchSourceBuilder);
//数查询
List<EsResponse<DelayLessAccessEntity>> esResponses = EsComponent.doc()
          .queryMulti("es-component-test", esSearchSourceBuilders, DelayLessAccessEntity.class);
```
4. aggQuery 聚合查询
```
参数：
· String             indexName          索引名
· EsAggSourceBuilder esAggSourceBuilder 聚合条件
· Class              tClass             doc数据返回类型，如果没有topHit聚合条件可为空
```
```
返回：
· EsResponse<T>
```
``` 
案例：
//创建查询条件
EsAggSourceBuilder esAggSourceBuilder = EsAggSourceBuilder.create();
TermsAggBuilder termsAggBuilder = EsAggQueryBuilders.terms("confIdTerms").field("confId");
TermsAggBuilder termsAggBuilder1 = EsAggQueryBuilders.terms("userIdTerms").keywordField("userId");
termsAggBuilder1.subAggregation(EsAggQueryBuilders.topHits("docHist").offset(10));
termsAggBuilder1.subAggregation(EsAggQueryBuilders.count("userAccessIdCount").field("userAccessId"), DefaultAggBuilderConvert.getInstance());
termsAggBuilder.subAggregation(termsAggBuilder1, DefaultAggBuilderConvert.getInstance());
termsAggBuilder.subAggregation(EsAggQueryBuilders.count("idCount").field("id"));
esAggSourceBuilder.agg(termsAggBuilder);
//数查询
EsAggResponse esAggResponse = EsComponent.doc()
       .aggQuery("es-component-test", esAggSourceBuilder,  DelayLessAccessEntity.class);
``` 
5. add 添加，不覆盖id相同项
```
参数：
· String id        id
· String indexName 索引名
· Object document  文档内容
```
```
返回：
· EsResponse
```
``` 
案例：
//doc文档
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(1514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest");
//添加
EsResponse esResponse = EsComponent.doc()
      .add("2514143394262159384", "es-component-test", entity);
``` 
6. add 添加，不覆盖id相同项
```
参数：
· String          id              id
· String          indexName       索引名
· Object          document        文档内容
· EsRefreshPolicy esRefreshPolicy 请求刷新策略
· int             retryTimes      失败重试次数
```
```
返回：
· EsResponse
```
``` 
案例：
//doc文档
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(1514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest");
//添加
EsResponse esResponse = EsComponent.doc().add("2514143394262159384",
             "es-component-test", entity, EsRefreshPolicy.IMMEDIATE, 1);
``` 
7. coverAndAdd 添加，覆盖id相同项
```
参数：
· String id        id
· String indexName 索引名
· Object document  文档内容
```
```
返回：
· EsResponse
```
``` 
案例：
//doc文档
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(1514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest");
//添加
EsResponse esResponse = EsComponent.doc()
      .coverAndAdd("2514143394262159384", "es-component-test", entity);
``` 
8. coverAndAdd 添加，覆盖id相同项
```
参数：
· String          id              id
· String          indexName       索引名
· Object          document        文档内容
· EsRefreshPolicy esRefreshPolicy 请求刷新策略
· int             retryTimes      失败重试次数
```
```
返回：
· EsResponse
```
``` 
案例：
//doc文档
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(1514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest");
//添加
EsResponse esResponse = EsComponent.doc().coverAndAdd("2514143394262159384",
             "es-component-test", entity, EsRefreshPolicy.IMMEDIATE, 1);
```
9. delete 删除
```
参数：
· String id        id
· String indexName 索引名
```
```
返回：
· EsResponse
```
``` 
案例：
//删除
EsResponse esResponse = EsComponent.doc()
      .delete("2514143394262159384", "es-component-test");
```
10. delete 删除
```
参数：
· String id                       id
· String indexName                索引名
· EsRefreshPolicy esRefreshPolicy 请求刷新策略
· int retryTimes                  失败重试次数
```
```
返回：
· EsResponse
```
``` 
案例：
//删除
EsResponse esResponse = EsComponent.doc().delete("2514143394262159384",
       "es-component-test", EsRefreshPolicy.IMMEDIATE, 1);
```
11. update 更新
```
参数：
· String id        id
· String indexName 索引名
· Object document  文档内容
```
```
返回：
· EsResponse
```
``` 
案例：
//doc文档
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setUserId("esDemoTest1");
//更新
EsResponse esResponse = EsComponent.doc().update("2514143394262159384",
            "es-component-test", entity);
```
12. update 更新
```
参数：
· String          id              id
· String          indexName       索引名
· String          document        文档内容
· EsRefreshPolicy esRefreshPolicy 请求刷新策略
· int             retryTimes      失败重试次数
```
```
返回：
· EsResponse
```
``` 
案例：
//doc文档
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setUserId("esDemoTest1");
//更新
EsResponse esResponse = EsComponent.doc().update("2514143394262159384",
            "es-component-test", entity, EsRefreshPolicy.IMMEDIATE, 1);
```
13. addBulk 批量添加，不覆盖id相同项
```
参数：
· Collection<DocWriteData> addCollection   添加数据集合（id, 索引，内容）
```
```
返回：
· int 成功条数
```
``` 
案例：
//批量基础信息
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//批量添加
int successCount = EsComponent.doc()
        .addBulk(docWriteDataCollection);
```
14. addBulk 批量添加，不覆盖id相同项
```
参数：
· Collection<DocWriteData> addCollection 添加数据集合（id, 索引，内容）
· EsRefreshPolicy          esRefreshPolicy 请求刷新策略
· int                      retryTimes      失败重试次数
```
```
返回：
· int 成功条数
```
``` 
案例：
//批量基础信息
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//批量添加
int successCount = EsComponent.doc()
        .addBulk(docWriteDataCollection, EsRefreshPolicy.IMMEDIATE, 1);
```
15. coverAndAddBulk 批量添加，覆盖id相同项
```
参数：
· Collection<DocWriteData> addCollection   添加数据集合（id, 索引，内容）
```
```
返回：
· int 成功条数
```
``` 
案例：
//批量基础信息
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//批量添加
int successCount = EsComponent.doc()
        .coverAndAddBulk(docWriteDataCollection);
```
16. coverAndAddBulk 批量添加，覆盖id相同项
```
参数：
· Collection<DocWriteData> addCollection 添加数据集合（id, 索引，内容）
· EsRefreshPolicy          esRefreshPolicy 请求刷新策略
· int                      retryTimes      失败重试次数
```
```
返回：
· int 成功条数
```
``` 
案例：
//批量基础信息
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//批量添加
int successCount = EsComponent.doc()
        .coverAndAddBulk(docWriteDataCollection, EsRefreshPolicy.IMMEDIATE, 1);
``` 
17. deleteBulk 批量删除
```
参数：
· Collection<DocWriteData> deleteCollection 删除数据集合（id, 索引）
```
```
返回：
· int 成功条数
```
``` 
案例：
//批量基础信息
Collection<DocWriteData> deleteWriteDataCollection = new ArrayList<>();
deleteWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", null));
deleteWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", null));
//批量删除
int successCount = EsComponent.doc()
            .deleteBulk(deleteWriteDataCollection);
```
18. deleteBulk 批量删除
```
参数：
· Collection<DocWriteData> deleteCollection 删除数据集合（id, 索引）
· EsRefreshPolicy          esRefreshPolicy  请求刷新策略
· int                      retryTimes       失败重试次数
```
```
返回：
· int 成功条数
```
``` 
案例：
//批量基础信息
Collection<DocWriteData> deleteWriteDataCollection = new ArrayList<>();
deleteWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", null));
deleteWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", null));
//批量删除
int successCount = EsComponent.doc()
            .deleteBulk(deleteWriteDataCollection, EsRefreshPolicy.IMMEDIATE, 1);
``` 
19. updateBulk 批量更新
```
参数：
· Collection<DocWriteData> updateCollection 添加数据集合（id, 索引，内容）
```
```
返回：
· int 成功条数
```
``` 
案例：
//批量基础信息
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//批量更新
int successCount = EsComponent.doc()
        .updateBulk(docWriteDataCollection);
```
20. updateBulk 批量更新
```
参数：
· Collection<DocWriteData> updateCollection 添加数据集合（id, 索引，内容）
· EsRefreshPolicy          esRefreshPolicy  请求刷新策略
· int                      retryTimes       失败重试次数
```
```
返回：
· int 成功条数
```
``` 
案例：
//批量基础信息
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//批量更新
int successCount = EsComponent.doc()
        .updateBulk(docWriteDataCollection, EsRefreshPolicy.IMMEDIATE, 1);
```
21. addAsync 异步添加，不覆盖id相同项
```
参数：
· String              id                  id
· String              indexName           索引名
· Object              document            文档内容
· AsyncActionListener asyncActionListener 异步回调监听器
```
```
回调：
· AsyncActionListener
```
``` 
案例：
//监听器
AsyncActionListener asyncActionListener = new AsyncActionListener() {
            @Override
            public void onResponse(EsResponse esResponse) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//doc文档
DelayLessAccessEntity delayLessAccessEntity = new DelayLessAccessEntity();
delayLessAccessEntity.setUserAccessId(22333L);
delayLessAccessEntity.setConfId(22333L);
delayLessAccessEntity.setId(22333L);
delayLessAccessEntity.setUserId("testEs");
delayLessAccessEntity.setStartTime(System.currentTimeMillis());
delayLessAccessEntity.setEndTime(System.currentTimeMillis() + 10000);
//异步添加
EsComponent.doc()
       .addAsync("22333", "es-component-test", delayLessAccessEntity, asyncActionListener);
```
22. addAsync 异步添加，不覆盖id相同项
```
参数：
· String              id                  id
· String              indexName           索引名
· Object              document            文档内容
· EsRefreshPolicy     esRefreshPolicy     请求刷新策略
· int                 retryTimes          失败重试次数
· AsyncActionListener asyncActionListener 异步回调监听器
```
```
回调：
· AsyncActionListener
```
``` 
案例：
//监听器
AsyncActionListener asyncActionListener = new AsyncActionListener() {
            @Override
            public void onResponse(EsResponse esResponse) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//doc文档
DelayLessAccessEntity delayLessAccessEntity = new DelayLessAccessEntity();
delayLessAccessEntity.setUserAccessId(22333L);
delayLessAccessEntity.setConfId(22333L);
delayLessAccessEntity.setId(22333L);
delayLessAccessEntity.setUserId("testEs");
delayLessAccessEntity.setStartTime(System.currentTimeMillis());
delayLessAccessEntity.setEndTime(System.currentTimeMillis() + 10000);
//异步添加
EsComponent.doc().addAsync("22333", "es-component-test"
       , delayLessAccessEntity, EsRefreshPolicy.IMMEDIATE, 1, asyncActionListener);
```
23. coverAndAddAsync 异步添加，覆盖id相同项
```
参数：
· String              id                  id
· String              indexName           索引名
· Object              document            文档内容
· AsyncActionListener asyncActionListener 异步回调监听器
```
```
回调：
· AsyncActionListener
```
``` 
案例：
//监听器
AsyncActionListener asyncActionListener = new AsyncActionListener() {
            @Override
            public void onResponse(EsResponse esResponse) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//doc文档
DelayLessAccessEntity delayLessAccessEntity = new DelayLessAccessEntity();
delayLessAccessEntity.setUserAccessId(22333L);
delayLessAccessEntity.setConfId(22333L);
delayLessAccessEntity.setId(22333L);
delayLessAccessEntity.setUserId("testEs");
delayLessAccessEntity.setStartTime(System.currentTimeMillis());
delayLessAccessEntity.setEndTime(System.currentTimeMillis() + 10000);
//异步添加
EsComponent.doc()
       .coverAndAddAsync("22333", "es-component-test", delayLessAccessEntity, asyncActionListener);
```
24. coverAndAddAsync 异步添加，覆盖id相同项
```
参数：
· String              id                  id
· String              indexName           索引名
· Object              document            文档内容
· EsRefreshPolicy     esRefreshPolicy     请求刷新策略
· int                 retryTimes          失败重试次数
· AsyncActionListener asyncActionListener 异步回调监听器
```
```
回调：
· AsyncActionListener
```
``` 
案例：
//监听器
AsyncActionListener asyncActionListener = new AsyncActionListener() {
            @Override
            public void onResponse(EsResponse esResponse) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//doc文档
DelayLessAccessEntity delayLessAccessEntity = new DelayLessAccessEntity();
delayLessAccessEntity.setUserAccessId(22333L);
delayLessAccessEntity.setConfId(22333L);
delayLessAccessEntity.setId(22333L);
delayLessAccessEntity.setUserId("testEs");
delayLessAccessEntity.setStartTime(System.currentTimeMillis());
delayLessAccessEntity.setEndTime(System.currentTimeMillis() + 10000);
//异步添加
EsComponent.doc().coverAndAddAsync("22333", "es-component-test"
       , delayLessAccessEntity, EsRefreshPolicy.IMMEDIATE, 1, asyncActionListener);
```
25. updateAsync 异步更新
```
参数：
· String              id                  id
· String              indexName           索引名
· Object              document            文档内容
· AsyncActionListener asyncActionListener 异步回调监听器
```
```
回调：
· AsyncActionListener
```
``` 
案例：
//监听器
AsyncActionListener asyncActionListener = new AsyncActionListener() {
            @Override
            public void onResponse(EsResponse esResponse) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//doc文档
DelayLessAccessEntity delayLessAccessEntity = new DelayLessAccessEntity();
delayLessAccessEntity.setUserAccessId(22333L);
delayLessAccessEntity.setConfId(22333L);
delayLessAccessEntity.setId(22333L);
delayLessAccessEntity.setUserId("testEs");
delayLessAccessEntity.setStartTime(System.currentTimeMillis());
delayLessAccessEntity.setEndTime(System.currentTimeMillis() + 10000);
//异步更新
EsComponent.doc()
       .updateAsync("22333", "es-component-test", delayLessAccessEntity, asyncActionListener);
```
26. updateAsync 异步更新
```
参数：
· String              id                  id
· String              indexName           索引名
· Object              document            文档内容
· EsRefreshPolicy     esRefreshPolicy     请求刷新策略
· int                 retryTimes          失败重试次数
· AsyncActionListener asyncActionListener 异步回调监听器
```
```
回调：
· AsyncActionListener
```
``` 
案例：
//监听器
AsyncActionListener asyncActionListener = new AsyncActionListener() {
            @Override
            public void onResponse(EsResponse esResponse) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//doc文档
DelayLessAccessEntity delayLessAccessEntity = new DelayLessAccessEntity();
delayLessAccessEntity.setUserAccessId(22333L);
delayLessAccessEntity.setConfId(22333L);
delayLessAccessEntity.setId(22333L);
delayLessAccessEntity.setUserId("testEs");
delayLessAccessEntity.setStartTime(System.currentTimeMillis());
delayLessAccessEntity.setEndTime(System.currentTimeMillis() + 10000);
//异步更新
EsComponent.doc().updateAsync("22333", "es-component-test"
       , delayLessAccessEntity, EsRefreshPolicy.IMMEDIATE, 1, asyncActionListener);
```
27. deleteAsync 异步删除
```
参数：
· String              id                  id
· String              indexName           索引名
· AsyncActionListener asyncActionListener 异步回调监听器
```
```
回调：
· AsyncActionListener
```
``` 
案例：
//监听器
AsyncActionListener asyncActionListener = new AsyncActionListener() {
            @Override
            public void onResponse(EsResponse esResponse) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//doc文档
DelayLessAccessEntity delayLessAccessEntity = new DelayLessAccessEntity();
delayLessAccessEntity.setUserAccessId(22333L);
delayLessAccessEntity.setConfId(22333L);
delayLessAccessEntity.setId(22333L);
delayLessAccessEntity.setUserId("testEs");
delayLessAccessEntity.setStartTime(System.currentTimeMillis());
delayLessAccessEntity.setEndTime(System.currentTimeMillis() + 10000);
//异步删除
EsComponent.doc()
       .deleteAsync("22333", "es-component-test", asyncActionListener);
```
28. deleteAsync 异步删除
```
参数：
· String              id                  id
· String              indexName           索引名
· EsRefreshPolicy     esRefreshPolicy     请求刷新策略
· int                 retryTimes          失败重试次数
· AsyncActionListener asyncActionListener 异步回调监听器
```
```
回调：
· AsyncActionListener
```
``` 
案例：
//监听器
AsyncActionListener asyncActionListener = new AsyncActionListener() {
            @Override
            public void onResponse(EsResponse esResponse) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//doc文档
DelayLessAccessEntity delayLessAccessEntity = new DelayLessAccessEntity();
delayLessAccessEntity.setUserAccessId(22333L);
delayLessAccessEntity.setConfId(22333L);
delayLessAccessEntity.setId(22333L);
delayLessAccessEntity.setUserId("testEs");
delayLessAccessEntity.setStartTime(System.currentTimeMillis());
delayLessAccessEntity.setEndTime(System.currentTimeMillis() + 10000);
//异步删除
EsComponent.doc().deleteAsync("22333", "es-component-test"
       , EsRefreshPolicy.IMMEDIATE, 1, asyncActionListener);
```
29. addBulkAsync 异步批量添加，不覆盖id相同项
```
参数：
· Collection<DocWriteData> addCollection           添加数据集合（id, 索引，内容）
· bulkAsyncActionListener  bulkAsyncActionListener 批量操作异步回调监听器
```
```
回调：
· BulkAsyncActionListener
```
``` 
案例：
//监听器
BulkAsyncActionListener bulkAsyncActionListener = new BulkAsyncActionListener() {
            @Override
            public void onResponse(int successCount) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//批量基础信息        
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//批量添加
 EsComponent.doc()
        .addBulkAsync(docWriteDataCollection, bulkAsyncActionListener);
```
30. addBulkAsync 异步批量添加，不覆盖id相同项
```
参数：
· Collection<DocWriteData> addCollection           添加数据集合（id, 索引，内容）
· EsRefreshPolicy          esRefreshPolicy         请求刷新策略
· int                      retryTimes              失败重试次数
· bulkAsyncActionListener  bulkAsyncActionListener 批量操作异步回调监听器
```
```
回调：
· BulkAsyncActionListener
```
``` 
案例：
//监听器
BulkAsyncActionListener bulkAsyncActionListener = new BulkAsyncActionListener() {
            @Override
            public void onResponse(int successCount) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//批量基础信息        
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//批量添加
EsComponent.doc()
        .addBulkAsync(docWriteDataCollection, EsRefreshPolicy.IMMEDIATE, 1, bulkAsyncActionListener);
```
31. coverAndAddBulkAsync 异步批量添加，覆盖id相同项
```
参数：
· Collection<DocWriteData> addCollection           添加数据集合（id, 索引，内容）
· bulkAsyncActionListener  bulkAsyncActionListener 批量操作异步回调监听器
```
```
回调：
· BulkAsyncActionListener
```
``` 
案例：
//监听器
BulkAsyncActionListener bulkAsyncActionListener = new BulkAsyncActionListener() {
            @Override
            public void onResponse(int successCount) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//批量基础信息        
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//异步批量添加
EsComponent.doc()
        .coverAndAddBulkAsync(docWriteDataCollection, bulkAsyncActionListener);
```
32. coverAndAddBulkAsync 异步批量添加，覆盖id相同项
```
参数：
· Collection<DocWriteData> addCollection           添加数据集合（id, 索引，内容）
· EsRefreshPolicy          esRefreshPolicy         请求刷新策略
· int                      retryTimes              失败重试次数
· bulkAsyncActionListener  bulkAsyncActionListener 批量操作异步回调监听器
```
```
回调：
· BulkAsyncActionListener
```
``` 
案例：
//监听器
BulkAsyncActionListener bulkAsyncActionListener = new BulkAsyncActionListener() {
            @Override
            public void onResponse(int successCount) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//批量基础信息        
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//异步批量添加
EsComponent.doc()
        .coverAndAddBulkAsync(docWriteDataCollection, EsRefreshPolicy.IMMEDIATE, 1, bulkAsyncActionListener);
```
33. updateBulkAsync 异步批量更新
```
参数：
· Collection<DocWriteData> updateCollection        更新数据集合（id, 索引，内容）
· bulkAsyncActionListener  bulkAsyncActionListener 批量操作异步回调监听器
```
```
回调：
· BulkAsyncActionListener
```
``` 
案例：
//监听器
BulkAsyncActionListener bulkAsyncActionListener = new BulkAsyncActionListener() {
            @Override
            public void onResponse(int successCount) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//批量基础信息        
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//异步批量更新
EsComponent.doc()
        .updateBulkAsync(docWriteDataCollection, bulkAsyncActionListener);
```
34. updateBulkAsync 异步批量更新
```
参数：
· Collection<DocWriteData> updateCollection        更新数据集合（id, 索引，内容）
· EsRefreshPolicy          esRefreshPolicy         请求刷新策略
· int                      retryTimes              失败重试次数
· bulkAsyncActionListener  bulkAsyncActionListener 批量操作异步回调监听器
```
```
回调：
· BulkAsyncActionListener
```
``` 
案例：
//监听器
BulkAsyncActionListener bulkAsyncActionListener = new BulkAsyncActionListener() {
            @Override
            public void onResponse(int successCount) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//批量基础信息        
DelayLessAccessEntity entity = new DelayLessAccessEntity();
entity.setConfId(2514120394947170326L);
entity.setEndTime(1649834936271L);
entity.setId(2514143394262159384L);
entity.setStartTime(1649834891190L);
entity.setUserAccessId(1514132486072832010L);
entity.setUserId("esDemoTest1");
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", entity));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", entity));
//异步批量更新
EsComponent.doc()
        .updateBulkAsync(docWriteDataCollection, EsRefreshPolicy.IMMEDIATE, 1, bulkAsyncActionListener);
```
35. deleteBulkAsync 异步批量删除
```
参数：
· Collection<DocWriteData> deleteCollection        删除数据集合（id, 索引）
· bulkAsyncActionListener  bulkAsyncActionListener 批量操作异步回调监听器
```
```
回调：
· BulkAsyncActionListener
```
``` 
案例：
//监听器
BulkAsyncActionListener bulkAsyncActionListener = new BulkAsyncActionListener() {
            @Override
            public void onResponse(int successCount) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//批量基础信息        
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", null));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", null));
//异步批量删除
EsComponent.doc()
        .deleteBulkAsync(docWriteDataCollection, bulkAsyncActionListener);
```
36. deleteBulkAsync 异步批量删除
```
参数：
· Collection<DocWriteData> deleteCollection        删除数据集合（id, 索引）
· EsRefreshPolicy          esRefreshPolicy         请求刷新策略
· int                      retryTimes              失败重试次数
· bulkAsyncActionListener  bulkAsyncActionListener 批量操作异步回调监听器
```
```
回调：
· BulkAsyncActionListener
```
``` 
案例：
//监听器
BulkAsyncActionListener bulkAsyncActionListener = new BulkAsyncActionListener() {
            @Override
            public void onResponse(int successCount) {
                System.out.println("test-Ok");
            }

            @Override
            public void onFailure(EsException esException) {
                System.out.println("test-Failure");
            }
        };
//批量基础信息        
Collection<DocWriteData> docWriteDataCollection = new ArrayList<>();
docWriteDataCollection.add(new DocWriteData("2514143394262159384", "es-component-test", null));
docWriteDataCollection.add(new DocWriteData("2514143394262159385", "es-component-test", null));
//异步批量删除
EsComponent.doc()
        .deleteBulkAsync(docWriteDataCollection, EsRefreshPolicy.IMMEDIATE, 1, bulkAsyncActionListener);
```

### index类
1. createIndex 创建默认索引(分片数：3，副本数：1，查询深度：10000000)
```
参数：
· String indexName 索引名
```
```
返回：
· boolean
```
``` 
案例：
//创建默认索引
boolean flag = EsComponent.indices().createIndex("es-component-test");
```
2. createIndex 创建索引
```
参数：
· indexName  索引名
· esSettings EsSettings
· esMappings EsMapping集合
```
```
返回：
· boolean
```
``` 
案例：
//创建索引
boolean flag = EsComponent.indices()
      .createIndex("es-component-test", EsSetting.defaultEsSettings(), null);
boolean indexFlag = EsComponent.indices()
      .createIndex("es-component-test2", null, null);
```
3. indexExists 判断索引是否存在
```
参数：
· String indexName 索引名
```
```
返回：
· boolean
```
``` 
案例：
//判断索引是否存在
boolean flag = EsComponent.indices().indexExists("es-component-test");
```
4. addAlias 为索引赋别名
```
参数：
· String indexName 索引名
· String alias     别名
```
```
返回：
· boolean
```
``` 
案例：
//为索引赋别名
boolean flag = EsComponent.indices()
       .addAlias("es-component-test2", "test-es-tool");
```
5. deleteIndex 删除索引
```
参数：
· String indexName 索引名
```
```
返回：
· boolean
```
``` 
案例：
//删除索引
boolean flag = EsComponent.indices().deleteIndex("es-component-test");
```