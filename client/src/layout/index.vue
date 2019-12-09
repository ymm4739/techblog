<template>
  <div>
    <Navigation />
    <router-view />
  </div>
</template>
<script>
import Navigation from './components/navigation'
export default {
  name: 'Layout',
  components: {
    Navigation
  }
}
</script>
