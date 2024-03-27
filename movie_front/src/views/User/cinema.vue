<template>
    <div class="cinemas">
      <div class="cinemas_select">
          <div class="tags">
              <span class="tags-title">品牌 :</span>
              <span 
                  v-for="(item, index) in brandList" 
                  :key="index" 
                  :class="selectBrand===item ? 'active' : ''" 
                  @click="handleBrandSelect(item)"
                  >
              <router-link class="tags_select" :to=" '/cinema?area='+ selectArea + '&brand=' + item ">{{ item }}</router-link>
            
            </span >
          </div>
          <div class="tags">
              <span class="tags-title">区域 :</span>
            <span v-for="(item,index) in areaList" 
            :key="index"
            :class="selectArea===item ? 'active' : ''"
                @click="handleAreaSelect(item)">
                <router-link class="tags_select" :to=" '/cinema?area='+ item + '&brand=' + selectBrand ">{{ item }}</router-link>
            </span>
          </div>
      </div>
      <div class="cinema_list">
        <div v-for="item in cinemaList" :key="item.cinemaId" class="cinema_item">
            <div style="display: flex; justify-content: space-between;" class="cinema_one">
            <div style="padding: 0 0 0 20px;">
                <router-link :to="'/cinema='+item.cinemaId" style="text-decoration: none" class="cinema_name">{{ item.name }}</router-link>
                <div class="cinema_address">{{ item.address }}</div>
            </div>
            <div style="padding: 0 20px 0 0;">
                <router-link :to="'/cinemaId='+item.cinemaId">
                <el-button style="background-color: #f03d37; border-radius: 30px;color: #fff;">选座购票</el-button>
            </router-link>
            </div>
        </div>
    </div>
    </div>
  </div>
  </template>
<script>
export default {
name: 'cinema',
data() {
  return {
    selectBrand: '全部',
    selectArea: '全部',
    brandList: ["全部", "万达影城", "大地影院"],
    areaList: ["全部", "朝阳区", "二道区", "南关区", "绿园区", "宽城区"],
    cinemaList: [],
  }
},

mounted() {
    
      this.$request.get('/cinema/all').then(res => { // 假设API路径是/api/movie/:id，:id是动态路由参数。
        console.log(res.data);
        this.cinemaList = res.data; // 更新Movie对象。// 更新评分组件的值。
      }).catch(error => { // 处理错误。
        console.error('获取电影信息失败：', error);
      });
    },
  methods: {

    handleBrandSelect(item) {
      this.selectBrand = item;
      this.reloadFilmList()
    },

    handleAreaSelect(item) {
      this.selectArea = item;
      this.reloadFilmList()
    },

    reloadFilmList() {
      this.$request.get('/cinema/selectByAAB',{ params: { area: this.selectArea, brand: this.selectBrand } }).then(res => {
        this.cinemaList = res
        console.log(this.cinemaList);

    }).catch(error => {
        console.error('获取电影列表失败：', error);
      });
      
    },

}
}
</script>

<style>
.cinemas{
    margin: 0 160px;
}
.cinemas_select{
    margin: 20px 0 0 0 ;
    height: 100px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.tags-title {
    height: 24px;
    line-height: 24px;
    float: left;
    color: #999;
    font-size: 14px;
}
.tags {
    width: 100%;
    height: 50px;
    padding: 0 40px;
    line-height: 24px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
}
.tags_select {
    padding: 0 10px;
    color: black;
    border: 1px solid transparent; /* 默认边框透明 */
    transition: all 0.3s ease; /* 添加过渡效果 */
    text-decoration: none;
}
.active {
    background-color: #f34d41; /* 被选中时的背景色 */
    border-radius: 40%;
    color: white; /* 被选中时的文字颜色 */
}

.cinema_list{
    margin: 30px 0 0 0 ;
}

.cinema_item{
    width: 100%;
}
.cinema_item:hover{
    background-color: #cfcfcf;
}
.cinema_one{
    height: 80px;
    padding: 10px 0;
    align-items: center;
}
.cinema_name{
    font-size: 18px;
    color: #333;
}
.cinema_address{
    font-size: 14px;
    color: #999;
}

</style>