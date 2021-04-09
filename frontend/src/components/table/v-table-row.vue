<template>
    <div class="v-table-row">
        <div class="row row__name">{{row_data.name}}</div>
        <div class="row row__description">{{row_data.description}}</div>
        <div class="row row__logo">{{row_data.logotype}}</div>
    </div>
</template>

<script>
export default {
    name: "v-table-row",
    props: {
        row_data:{
            type: Object,
            default: () => {
                return {}
            }
        }
    }
}
</script>

<style>
    .v-table-row {
        display: flex;
        justify-content: space-around;
    }
    .row {
        flex-basis: 33%;
        text-align: left;
    }
</style>