<template>
  <div>
    <Navigation />
    <router-view />
  </div>
</template>
<script>
import Navigation from './components/Navigation'
export default {
  name: 'Layout',
  components: {
    Navigation
  }
}
</script>
