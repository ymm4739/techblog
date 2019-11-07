<template>
  <div>
    <h1>Home {{name}}</h1>
    <button @click="$router.push({name:'HelloWorld'})">Vue</button>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
    return {
      name: 'Youfan'
    }
  }
}
</script>

<style scoped>
</style>
