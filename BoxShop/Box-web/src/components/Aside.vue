<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Aside",
  data() {
    return {}
  },
  computed: {
    menuItems() {
      return this.$store.state.menu;
    },
    subMenuItems() {
      // 只包含特定的两条信息在subMenuItems中
      return this.menuItems.filter(item => item.menuclick === 'Storage' || item.menuclick === 'Goods');
    },
    outerMenuItems() {
      // 不包含特定的两条信息在outerMenuItems中
      return this.menuItems.filter(item => item.menuclick !== 'Storage' && item.menuclick !== 'Goods');
    },
    roleId() {
      // 权限信息,从而达到谁能看哪些页面的效果
      return this.$store.state.user.roleId;
    }
  },
  props: {
    isCollapse: Boolean
  },
  created() {
    const storedMenu = JSON.parse(localStorage.getItem('menu'));
    if (storedMenu) {
      this.$store.commit('setMenu', storedMenu);
    }
  }
}
</script>

<template>
  <el-menu
      :collapse="isCollapse"
      :collapse-transition="false"
      active-text-color="#ffd04b"
      background-color="#545c64"
      class="el-menu1"
      default-active="/Home"
      router
      style="height: 100vh;user-select: none;"
      text-color="#fff"
  >

    <el-menu-item index="/index">
      <i class="el-icon-s-home"></i>
      <span>主页</span>
    </el-menu-item>

    <el-submenu v-if="roleId === 0 || roleId === 1" index="sub1">
      <template slot="title">
        <i class="el-icon-star-on"></i>
        <span>仓库中心</span>
      </template>
      <!-- 循环渲染属于其他菜单的项 -->
      <el-menu-item v-for="(item, i) in subMenuItems" :key="i" :index="'/' + item.menuclick">
        <i :class="item.menuicon"></i>
        <span>{{ item.menuname }}</span>
      </el-menu-item>
    </el-submenu>

    <!-- 循环渲染不属于其他菜单的项 -->
    <el-menu-item v-for="(item, i) in outerMenuItems" :key="i" :index="'/' + item.menuclick">
      <i :class="item.menuicon"></i>
      <span>{{ item.menuname }}</span>
    </el-menu-item>

  </el-menu>
</template>

<style scoped>

</style>
