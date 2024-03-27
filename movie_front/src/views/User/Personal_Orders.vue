<template>
    <div>
        <el-card>

        </el-card>
    </div>
</template>

<script>
export default {
    name: 'Personal_Orders',
    data () {
        return {
            user:JSON.parse(localStorage.getItem('Moive_user') || '{}'),   //从浏览器的本地存储中获取token（用户）信息，
        }
    },
    created () {
    },
    methods: {
    }

}
</script>

<style>

</style>