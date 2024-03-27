<template>
    <div class="order-manager">
        <el-card class="box-card">
            <el-table :data="orderList"  stripe>
                <el-table-column prop="order_id" label="订单号"></el-table-column>
                <el-table-column prop="moive_name" label="电影名"></el-table-column>
                <el-table-column prop="order_amount" label="金额"></el-table-column>
                <el-table-column prop="order_account" label="下单账号"></el-table-column>
                <el-table-column prop="order_time" label="下单时间"></el-table-column>
                <el-table-column label="操作">
                    <template v-slot="scope">
                        <el-button type="primary" @click="detailed(scope.row)" plain>详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script>
export default {
name: 'OrderManager',
data () {
    return {
        orderList: []
    }
    },
created () {

},

methods: {

}
}
</script>

<style>
.el-card {
    height: 91.2vh;
}
</style>