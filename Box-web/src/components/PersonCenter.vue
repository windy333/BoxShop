<template>
  <div class="person-center">
    <div class="header">
      <h1><i class="el-icon-user"></i> 个人资料</h1>
      <i class="el-icon-refresh edit-icon" @click="startEditing"></i>
    </div>

    <el-descriptions :column="1" border>
      <el-descriptions-item label="账号 ID">{{ userId }}</el-descriptions-item>
      <el-descriptions-item label="登录账号">{{ userNo }}</el-descriptions-item>
      <el-descriptions-item label="姓名">{{ userName }}</el-descriptions-item>
      <el-descriptions-item label="联系方式">{{ userPhone }}</el-descriptions-item>
      <el-descriptions-item label="性别">
        <el-tag :type="userSex === 1 ? 'primary' : 'danger'">
          {{ userSex === 1 ? '男' : '女' }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="权限">
        <el-tag disable-transitions type="success">
          {{ userRole === 0 ? '超级管理员' : (userRole === 1 ? '管理员' : '用户') }}
        </el-tag>
      </el-descriptions-item>
    </el-descriptions>

    <el-dialog
        :visible.sync="editDialogVisible"
        center
        title="修改个人信息"
        width="30%"
    >
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="editForm.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="editForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="saveChanges">保存</el-button>
        <el-button @click="cancelEditing">取消</el-button>
      </div>
    </el-dialog>

    <!--            <el-alert
                            :closable="false"
                            style="margin: 10px;"
                            title=
                                "菜单——这个项目建立于我大三下学期并花了我一个多月左右的时间，灵感来源于其他的管理系统作品，
                                开始是想对springboot进行学习和做一定的了解，后来加入了前端页面做成了一个大的项目，
                                并不断完善功能和改进使用体验。从中我也学到了很多(尤其是前端的知识点，虽然没有完全
                                掌握)，目前这个项目也快接近尾声了，但还是有很多我希望设计或者完善的地方，奈何能力有
                                限，尽力而为了。最后也希望这个作品表现的比较出色。"
                            type="info">
                        </el-alert> -->
  </div>
</template>

<script>
export default {
  name: "PersonCenter",
  computed: {
    userName() {
      return this.$store.state.user ? this.$store.state.user.name : 'Guest';
    },
    userId() {
      return this.$store.state.user ? this.$store.state.user.id : '';
    },
    userNo() {
      return this.$store.state.user ? this.$store.state.user.no : '';
    },
    userSex() {
      return this.$store.state.user ? this.$store.state.user.sex : '';
    },
    userPhone() {
      return this.$store.state.user ? this.$store.state.user.phone : '';
    },
    userRole() {
      return this.$store.state.user ? this.$store.state.user.roleId : '';
    },
  },

  data() {
    return {
      editDialogVisible: false,
      editForm: {
        id: '',
        name: '',
        password: '',
        phone: '',
      },
    };
  },

  methods: {
    startEditing() {
      this.editForm = {
        id: this.userId,
        name: this.userName,
        password: '',
        phone: this.userPhone,
      };
      this.editDialogVisible = true;
    },

    saveChanges() {
      if (!this.editForm.password) {
        this.$message({
          message: '请输入新密码！',
          type: 'warning'
        });
      } else {
        const data = {
          id: this.editForm.id,              //同步获取当前id才能修改信息
          password: this.editForm.password, //编辑后的信息
          phone: this.editForm.phone,
        };
        this.$axios.post(this.$httpUrl + '/user/update', data)
            .then(res => res.data)
            .then(res => {
              console.log(res);
              if (res.code === 200) {
                this.$message({
                  message: '保存成功！请尽快重新登录',
                  type: 'success'
                });
                // 保存成功后的操作
                this.editDialogVisible = false;
              } else {
                this.$message({
                  message: '保存失败！',
                  type: 'error'
                });
              }
            });
      }
    },
    cancelEditing() {
      this.editDialogVisible = false;
    }
  }

}
</script>


<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  margin-top: 20px;
}

.person-center h1 {
  margin-left: 70px;
  font-size: 24px;
  margin-bottom: 20px;
}

.person-center {
  max-height: 600px; /* 设置最大高度*/
  overflow: auto; /* 添加滚动条，内容超出时可以滚动查看 */
  text-align: center;
  background-color: #f1f1f3;
  height: 480px;
  padding: 20px;

}

.person-center .el-descriptions {
  width: 90%;
  margin: 0 auto;
  text-align: center;
  font-size: 18px; /* 调整表格文字大小 */
}

.edit-icon {
  user-select: none;
  cursor: pointer;
  margin-right: 70px;
  font-size: 20px;
}

</style>