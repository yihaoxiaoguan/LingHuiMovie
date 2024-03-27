<template>
  <div>
    <el-descriptions class="margin-top" title="带边框列表" :column="1" :size="size" border>
    <template slot="extra">
      <el-button type="primary" size="small" @click="$router.push('/Personal/PerUpdate')">编辑</el-button>
    </template>
    <el-descriptions-item>
      <template slot="label"><i class="el-icon-user"></i>头像</template>
      <img :src="user.avatar" style="width: 100px; height: 100px; border-radius: 50%;" />
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label"><i class="el-icon-user"></i>用户名</template>
      {{user.username}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        年龄
      </template>
      {{user.age}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        生日
      </template>
      {{user.birthday}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        手机号码
      </template>
      {{user.phone}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-location-outline"></i>
        居住地
      </template>
      {{user.address}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        备注
      </template>
      <el-tag size="small">学校</el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-office-building"></i>
        联系地址
      </template>
      江苏省苏州市吴中区吴中大道 1188 号
    </el-descriptions-item>
  </el-descriptions>
</div>
</template>

<script>
export default {
    name: 'Personal_Info',
    data () {
        return {
          user: JSON.parse(localStorage.getItem('Moive_user') || '{}') //从浏览器的本地存储中获取token（用户信息）
        }
    }

}
</script>

<style>

</style>