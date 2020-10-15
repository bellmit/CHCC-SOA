<template></template>
<script>
import swagger_path from '@/api/api'

export default {
    created: function() {
        window.open(swagger_path,'_blank');
    }
}
</script>