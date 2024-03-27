<template>
    <div>
        <div style="display: flex;width: 100%;">
            <div class="main_box" style="width: 50%;flex: 1;">
                <el-card>
                    <div class="main_box_title" slot="header">
                        <span>用户列表</span>
                    </div>
                    <div class="main_content">
                        <el-table :data="users">
                            <el-table-column label="ID" prop="id" width="35"></el-table-column>
                            <el-table-column label="用户名" prop="username"></el-table-column>
                            <el-table-column label="姓名" prop="name"></el-table-column>
                            <el-table-column label="手机号" prop="phone"></el-table-column>
                            <el-table-column label="地址" prop="address"></el-table-column>
                        </el-table>
                    </div>
                </el-card>
            </div>
            <div class="main_upload" style="width: 50%; flex: 1;">
                <el-card>
                    <div class="main_upload_title" slot="header">
                        <span>上传文件</span>
                    </div>
                    <div class="main_upload_content">
                        <el-upload action="http://localhost:8081/file/upload" :headers="{ token: UploadUser.token }"
                            list-type="picture" :on-success="handleFileUpload" :file-list="fileList">
                            <el-button size="small" type="primary">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                        </el-upload>
                    </div>
                </el-card>
            </div>
        </div>
    </div>
</template>
<script>
//引入request统一返回对象
import request from '@/utils/request';

export default {
    name: "UserManage",
    // 组件的JavaScript代码
    data() {
        return {
            users: [],
            UploadUser: JSON.parse(localStorage.getItem('Moive_user') || '{}'),
            fileList: []
        }
    },
    mounted() {
        // 页面加载后触发
        // axios.get('http://localhost:8081/user/selectAll').then(res => {
        //     console.log(res.data); // 打印出后台返回的数据 
        //     this.user = res.data.data;
        request.get('/user/selectAll').then(res => {
            this.users = res.data
        })
    },
    methods: {
        handleFileUpload(response, file, fileList) {
            this.fileList = fileList;
        }

    }

}


</script>

<style>
.main_box {
    width: 100%;
    margin: 1px;
    border: 1px solid #ccc;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    padding: 10px;
}
</style>