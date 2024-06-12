<!--  这里包含了Main,Aside，Header三个块，并进行分开管理-->

<template>
  <el-container>

    <el-header style="text-align: right; font-size: 12px;height: 100%">
      <Header :icon="icon" @doCollapse="doCollapse"></Header>
    </el-header>

    <!-- 添加虚线分隔线 -->
    <div style="border-bottom: 1px dashed #d3d6de;"></div>

    <el-container>
      <el-aside :width="aside_width"
                style="background-color: rgb(238, 241, 246);
                height:100%">
        <Aside :isCollapse="isCollapse"></Aside>
      </el-aside>
      <el-main style="height: 100%">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>

import Aside from "./Aside.vue";
import Header from "./Header.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Index",
  // eslint-disable-next-line vue/no-unused-components
  components: {Header, Aside},
  data() {
    return {
      isCollapse: true,
      aside_width: '64px',
      icon: "el-icon-s-unfold"
    }

  },
  methods: {
    doCollapse() {
      //导航页面展开功能
      this.isCollapse = !this.isCollapse
      if (!this.isCollapse) {
        this.aside_width = '200px'
        this.icon = "el-icon-s-fold"
      } else {
        this.aside_width = '64px'
        this.icon = "el-icon-s-unfold"
      }
    }
  }

};
</script>

<style scoped>
.el-header {
  line-height: 60px;
}

.el-aside {
  transition: width 0.3s; /*导航页面动画过渡效果*/
  color: #333;
  overflow: hidden;
}

</style>
