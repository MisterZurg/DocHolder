<template>
    <div class="main-index" align="center">
        <h2>Authors</h2>
        <md-table-container>
            <md-table-cell>
                <md-card>
                    <md-card-header>
                        <md-card-header-text>
                            <div class="md-title">Denis Zakharov</div>
                            <div class="md-subhead">
                                An undergraduate student at SPbSUT,<br/>
                                future security software engineer<br/>
                                and longlife learner<br/>
                            </div>
                        </md-card-header-text>
                        <md-card-media>
                            <md-badge class="md-primary" md-position="bottom" md-content="99+">
                                <img src="https://avatars.githubusercontent.com/u/62078213?v=4" alt="People">
                                <md-tooltip md-direction="bottom">@MisterZurg</md-tooltip>
                            </md-badge>
                        </md-card-media>
                    </md-card-header>

                    <md-card-actions>
                        <!--
                        <font-awesome-icon icon="coffee" />
                        -->
                        <a href="https://github.com/MisterZurg">
                            <md-button>
                                <md-icon>
                                    <img src="../icons/github-brands.svg">
                                </md-icon>
                            </md-button>
                        </a>
                    </md-card-actions>
                </md-card>
            </md-table-cell>
            <md-table-cell>
                <md-card>
                    <md-card-header>
                        <md-card-header-text>
                            <div class="md-title">Vladislav Usoltsev</div>
                            <div class="md-subhead">
                                An undergraduate student at AMSUMIS,<br/>
                                future senior software engineer <br/>
                                and longlife learner
                            </div>
                        </md-card-header-text>

                        <md-card-media>
                            <md-badge class="md-primary" md-position="bottom" md-content="99+">
                                <img src="https://avatars.githubusercontent.com/u/47406394?v=4" alt="Avatar">
                                <md-tooltip md-direction="bottom">@exodea</md-tooltip>
                            </md-badge>
                        </md-card-media>
                    </md-card-header>

                    <md-card-actions>
                        <a href="https://github.com/exodea">
                            <md-button>
                                <md-icon>
                                    <img src="../icons/github-brands.svg">
                                </md-icon>
                            </md-button>
                        </a>
                    </md-card-actions>
                </md-card>
            </md-table-cell>
        </md-table-container>
        <br/>
        <br/>
        <div class="demo-badge">
            <div>
                <h2>How did DocHolder started?</h2>
                <br/>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit.<br/>
                    Error quibusdam, non molestias et! Earum magnam, similique,<br/>
                    quo recusandae placeat dicta asperiores modi sint ea repudiandae maxime?<br/>
                    Quae non explicabo, neque.
                </p>
            </div>
            <br/>
            <div align="center">
                <h2>Thanks to</h2>
                <br/>
                <md-badge class="md-primary" md-content="inf+">
                    <md-avatar class="md-large">
                        <img src="https://avatars.githubusercontent.com/u/17143544?v=4" alt="Avatar ">
                        <md-tooltip md-direction="bottom">@nestdimmy</md-tooltip>
                    </md-avatar>
                </md-badge>
            </div>
            <br/>
            <div>
                <router-link to='/main-page'>
                    <md-button class="md-icon-button">
                        <md-icon>home</md-icon>
                    </md-button>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>

document.title = "Home Page";

export default {
    name: 'index',
    props: {},
    data() {
        return {}
    },
    mounted: function () {
    }
}


</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<!--
npm install
npm run serve
-->
<style>
/*
Color Palette for Bright Theme
https://colorhunt.co/palette/226680
fcf8ec  /   d0e8f2  /   79a3b1  /   456268
 */
body {
    background: #fcf8ec;
}

div {
    color: #79a3b1;
}

p {
    align-items: flex-start;
}


</style>


<!-- https://vuejsexamples.com/turning-web-forms-into-conversations-using-vue-js/ -->
<!--    https://officert.github.io/vue-slideout-panel/#/install -->
<!--    https://vuejsexamples.com/amazing-collection-of-vue-spinners-components-of-css-spinners-for-loading/ -->

<!--login: https://vuejsexamples.com/an-administrator-dashboard-implemented-by-vue-js/ -->

<!-- https://vuejsexamples.com/responsive-admin-dashboard-template-with-vue-js/ -->
<!-- https://vuejsexamples.com/responsive-admin-dashboard-template-with-vue-js/ -->