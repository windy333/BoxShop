<template>

  <div class="HeaderTotal" style="display: flex;line-height: 60px;">

    <div style="margin-top: 8px">
      <i :class="icon" style="cursor: pointer;margin-left: 3px;font-size: 20px" @click="collapse"></i>
    </div>

    <template>
      <div style="flex: 1; text-align: center; font-size: 34px; display: flex; align-items: center;">
        <router-link to="/index">
          <img :src="BoxShop" style="width: 125px; height: 50px; margin-left: 25px; display: block;">
        </router-link>
      </div>
    </template>

    <template>
      <div>    
        <i class="el-icon-full-screen" 
          style="margin-right: 10px;margin-top: 21px;font-size: 25px;cursor: pointer" 
          @click="toggleFullScreen">
        </i>
      </div>
    </template>
    

    <el-dropdown style="padding: 5px">
      <span style="cursor: pointer">{{ userName }}</span>
      <i class="el-icon-arrow-down" style="margin-left: 6px;"></i>
      <el-dropdown-menu slot="dropdown" style="user-select: none; ">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logOut">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  data() {
    return {
      BoxShop: require("../assets/BoxShop.png")
    };
  },
  props: {
    icon: String
  },
  computed: {
    userName() {
      return this.$store.state.user ? this.$store.state.user.name : 'Guest';
    }
  },
  methods: {
    toUser() {
      if (this.$route.path !== '/PersonCenter') {
        this.$router.push('/PersonCenter');
      }
    },
    logOut() {
      this.$confirm('您确定要退出登录吗?', '提示', {
        confirmButtonText: '确定',  //确认按钮的文字显示
        type: 'warning',
        center: true,
      }).then(() => {
            this.$message({
              type: 'success',
              message: '退出登录成功',
              duration: 1000
            })
            this.$router.push("/")
            sessionStorage.clear()//清空存储数据(缓存)
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消退出登录'
            })
          })
    },
    collapse() {
      this.$emit("doCollapse")
    },
    toggleFullScreen() {
       const elem = document.documentElement;
       if (!document.fullscreenElement) {
         elem.requestFullscreen(); 
        } else {
        document.exitFullscreen();
        }
     },
  },

}
</script>


<style scoped>
.HeaderTotal {
  user-select: none; /*不可选中文本*/
}
</style>