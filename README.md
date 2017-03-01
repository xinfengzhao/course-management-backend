## 

课程接口

# 概念说明

## 课程
指学员可以选择的科目. 不同的年级课程不同, 不同的地区课程也不同, 课时单价也不同.

```

  organization*: 机构ID,
  profile*: 配置文件, 比如采用不同的教材的地区, profile不同
  name: 课程名称
  price: 课时单价(分/小时)
  points: 可选属性: 积分, 默认值与(小时*单价)的正比
```

### organization
可选字段, 不存在表示全局基础课程COMMON; 否则为该机构私有课程设置.

# 接口设计

## PUT /courses
通过此接口创建新的课程

```
/courses   *grade为可选  其他都为必选    默认的一套课程 organization值为 COMMON
  body{
  	  "stage": "高中",  
	  "grade": "一年级",
	  "type": "课内",
	  "organization":"COMMON",
	  "name": "语文",
	  "price": 50,
	  "hours": 2,
  }
  
回复：
  {
  	code:0
  	message:"ok"
  	result:{
        id: "5f678ab789d798ce"
        stage: "高中",
        grade: "一年级",
        type: "课内",
        organization: "COMMON",
        name: "语文",
        price: 50,
        hours: 2,
        createdTime: 124123412312`
  	}
  	  
  }
  
 如果必选项没有填
  
 回复：
  {"code":-11002,
  "message":"properties stage,type,organization,name,price,hours can not be empty!",
  "result":null}
  

 如果课程已存在
  
 回复：
  {"code":-11003,
  "message":"course already exist!",
  "result":{
     "id":"57ff3139ff2ba440a89f8e88",
     "organization":"COMMON",
     "name":"语文",
     "stage":"高中",
     "grade":"一年级",
     "type":"课内",
     "price":8000,
     "hours":7200,
     "createdTime":1476342073593
     }
  } 
```

## POST /courses/{id}
通过此接口修改已有的课程

```
/courses/{id}  *可修改单个属性
  body{
	  "price": 60
  }
  
回复：
  {
  	code:0
  	message:"course already changed!"
  	result:{
        id: "5f678ab789d798ce"
        stage: "高中",
        grade: "一年级",
        type: "课内",
        organization: "COMMON",
        name: "语文",
        price: 60,
        hours: 2,
        createdTime: 124123412312`
  	}
  	  
  }
```

## GET /courses/{id}
## GET /courses?{params}

通过此接口获取满足条件的课程

params:
    stage  指定是哪个年级段的
    grade  制定具体哪个年级（和stage成对出现）
    type   指定哪种类型的课程
    price  指定课程的（一般不用）
    hours  指定课程的最短时长（一般不用）
    organization 默认值为COMMON
    limit  实现分页获取 默认limit=0,8

```
GET /courses?stage=高中&grade=一年级&type=课内

回复：
	{
	 "code":0,
	 "message":"OK",
	 "result":[
	  	{
            id: "5f678ab789d798ce",
            stage: "高中",
            grade: "一年级",
            type: "课内",
            organization: "COMMON",
            name: "语文",
            price: 60,
            hours: 2,
            createdTime: 124123412312`
	  	},
	  	{
            id: "5f678ab2354435d798ce",
            stage: "高中",
            grade: "一年级",
            type: "课内",
            organization: "COMMON",
            name: "数学",
            price: 60,
            hours: 2,
            createdTime: 1241242124`
	  	}
        ......	  
	  ],
	  "count":6,
	  "next":null
	}

  
```
## DELETE /courses/{id}
通过此接口删除已有的课程

```
  
回复：
  {"code":0,
  "message":"course delete successful!",
  "result":null}
```