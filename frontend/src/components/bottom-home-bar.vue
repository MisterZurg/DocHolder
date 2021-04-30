<template>
    <div>
        <!--
        <div class="phone-viewport">
            <md-bottom-bar md-type="shift">
                <router-link to='/main-page'>
                    <md-bottom-bar-item id="bottom-bar-item-home" md-label="Home" md-icon="home"></md-bottom-bar-item>
                </router-link>

                <md-bottom-bar-item id="bottom-bar-item-pages" md-label="Pages" md-icon="pages"></md-bottom-bar-item>
                <md-bottom-bar-item id="bottom-bar-item-posts" md-label="Posts" md-icon="/assets/icon-whatshot.svg"></md-bottom-bar-item>
                <md-bottom-bar-item id="bottom-bar-item-favorites" md-label="Favorites" md-icon="favorite"></md-bottom-bar-item>

            </md-bottom-bar>
        </div> -->


         <router-link to='/main-page'>
            <md-button class="md-icon-button">
                <md-icon>home</md-icon>
            </md-button>
        </router-link>
    </div>
</template>

<script>
export default {
    name: 'Shift'
}
</script>

<style lang="scss" scoped>
.phone-viewport {
    //width: 322px;
    //height: 200px;
    display: inline;
    align-items: flex-end;
    overflow: hidden;
    border: 0px solid rgba(#000, .26);
    background: rgba(#000, .06);
}
</style>