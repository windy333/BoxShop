<template>
  <div id="app">
    <router-view/>   <!--在App.vue中使用<router-view>来展示当前路由的组件-->
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {}

}
</script>

<style>
#app {
  height: 100%;
}
</style>
