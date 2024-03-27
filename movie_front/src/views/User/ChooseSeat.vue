<template>
    <div>
        <el-container style="justify-content: center;">
            <el-main class="C_seat_main">
                <div style="margin: 50px;">
                    <el-steps :active="1" align-center finish-status="success">
                        <el-step title="选择影片场次"></el-step>
                        <el-step title="选择座位"></el-step>
                        <el-step title="14分钟内付款"></el-step>
                        <el-step title="影院取票观影"></el-step>
                    </el-steps>
                </div>
                <div class="C_seat_body">
                    <div class="C_seat_seat">
                        <div>
                            <div style="display: flex; flex-direction: row;"></div>
                            <div class="seat-row" v-for="(seatitem, seatIndex) in 20" :key="seatIndex">
                                <i  class="el-icon-zuowei"></i>
                            </div>  
                        </div>
          </div>
                    <div class="C_seat_otherinfo">
                        <div style="margin: 20px;">
                            <div style="display: flex; flex-direction: row;">
                                <div><img class="movie_cover" :src="MovieInfo.poster"></div>
                                <div style="margin-left: 20px;">
                                    <div style="font-size: 22px; font-weight: bold;">{{ MovieInfo.movieName }}</div>
                                    <div style="font-size: 18px;"><span>时长：{{ MovieInfo.duration }}分钟</span></div>
                                    <div style="font-size: 18px;">类型：{{ MovieInfo.type }}</div>
                                </div>
                            </div>

                            <div>
                                <div style="font-size: 18px; ">影院：{{ CinemaInfo.name }}</div>
                                <div style="font-size: 18px; ">影厅：{{ showtimeInfo.hallName }}</div>
                                <div style="font-size: 18px; ">版本：{{ showtimeInfo.screenType }}</div>
                                <div style="font-size: 18px; ">场次：{{ showtimeInfo.startTime }} ~ {{ showtimeInfo.endTime
                                    }}</div>
                                <div style="font-size: 18px; ">票价：{{ showtimeInfo.price }}</div>
                            </div>
                            <div style="margin-top: 20px; border:after：1px">
                                <div style="font-size: 18px;">座位：</div>
                                <div style="display: flex; flex-direction: row; height: 100px;width: 100%; "></div>
                                <div style="font-size: 18px;">总价：{{ }}</div>
                            </div>
                            <div style="margin-top: 20px; display: flex; flex-direction: column; ">
                                <div style="font-size: 18px;justify-content: flex-start;">手机号：</div>
                                <el-button type="primary"
                                    style=" width: 80%; height: 45px; border-radius: 20px;margin-top: 35px;align-items: center;justify-content: center;">确认选座</el-button>
                            </div>
                        </div>
                    </div>
                </div>
            </el-main>
        </el-container>
    </div>
</template>

<script>
export default {
    name: 'ChooseSeat',
    data() {
        return {
            Ticket: {

            },
            MovieInfo: {},
            CinemaInfo: {},
            showtimeInfo: {},
            seatRows: [
            // 初始座位数组
        
      ],
           
        }
    },
    created() {
        this.getMovieInfo();
        this.getCinemaInfo();
        this.getShowtimeInfo();
    },
    methods: {
        getMovieInfo() {
            this.$request.get('/film/' + this.$route.params.movieId).then(res => {
                this.MovieInfo = res; // 更新Movie对象。
            }).catch(error => { // 处理错误。
                console.error('获取电影信息失败：', error);
            });
        },
        getCinemaInfo() {
            this.$request.get('/cinema/selectById/' + this.$route.params.cinemaId).then(res => {
                this.CinemaInfo = res; // 更新Movie对象。
            }).catch(error => { // 处理错误。
                console.error('获取影院信息失败：', error);
            });
        },
        getShowtimeInfo() {
            this.$request.get('/showtime/selectInfo', {
                params: {
                    showtimeId: this.$route.params.showtimeId,
                }
            }).then(res => {
                this.showtimeInfo = res; // 更新Movie对象。
            }).catch(error => { // 处理错误。
                console.error('获取场次信息失败：', error);
            });
        },
        selectSeat(rowIndex, seatIndex) {
      const seat = this.seatRows[rowIndex][seatIndex];
      seat.isSelected = !seat.isSelected; // 切换座位的选中状态
    },
    }
}


</script>

<style>
* {
    box-sizing: border-box;
}

.movie_cover {
    height: 158px;
    width: 115px;
}

.C_seat_main {
    margin-left: 6%;
    margin-right: 6%;
}

.C_seat_body {
    height: 640px;
    display: flex;
    border: 1px solid #e5e5e5;
    border-radius: 20px;
}

.C_seat_seat {
    width: 70%;
}

.C_seat_otherinfo {
    width: 30%;
    background-color: #f3f7fb;
    border-radius: 0 20px 20px 0;
}

.seat {
    /* 未选中座位的样式 */
}

.seat-selected {
    /* 选中座位的样式 */
    background-color: #f00;
    /* 例如设置为红色背景 */
}
</style>