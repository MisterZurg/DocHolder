<template>
    <div>
        <md-dialog :md-active.sync="showDialog">
            <md-dialog-title>Preferences</md-dialog-title>

            <md-tabs md-dynamic-height>
                <md-tab md-label="Logo">
                    <md-field>
                        <label>Only images</label>
                        <md-file v-model="newLogo" @md-change="setLogoBinary($event)" accept="image/*"/>
                    </md-field>
                    <md-button class="md-primary" @click="uploadLogo();">Upload</md-button>
                </md-tab>

                <md-tab md-label="General">
                    <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Ullam mollitia dolorum dolores quae
                        commodi impedit possimus qui, atque at voluptates cupiditate. Neque quae culpa suscipit
                        praesentium inventore ducimus ipsa aut.</p>
                </md-tab>

                <md-tab md-label="Staff">
                    <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Ullam mollitia dolorum dolores quae
                        commodi impedit possimus qui, atque at voluptates cupiditate. Neque quae culpa suscipit
                        praesentium inventore ducimus ipsa aut.</p>
                </md-tab>
            </md-tabs>

            <md-dialog-actions>
                <md-button class="md-primary" @click="showDialog = false">Close</md-button>
                <md-button class="md-primary" @click="showDialog = false">Save</md-button>
<!--                <md-button class="md-raised button-edit md-primary" v-on:click="saveInfo();" ref="saveInfo" disabled>Сохранить</md-button>-->

            </md-dialog-actions>
        </md-dialog>

        <md-button class="md-accent md-raised" @click="showDialog = true">Finally Update 2</md-button>
    </div>
</template>

<script>
export default {
    name: 'DialogCustom',
    data: () => ({
        showDialog: false,
        documentBinary: null,
        logoBinary: null
    }),
    methods: {
        getCompany(){
            // test ! then delete!
            if(localStorage.token == undefined) return;

            var query = this.$http(
                {
                    method: 'get',
                    url: 'http://localhost:8082/company/read?id='+this.$route.query.id,
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(function(response) {return response;})
                .catch(function(error) {return error.response;});

            query.then((response) => {
                let status = response.status;

                if(status == 404){
                    // console.log("catch error 404");
                    return;
                }

                if(status == 200){
                    let data = response.data;
                    this.companyName = data.name;
                    this.description = data.description;
                    this.logo = "data:image/jpeg;base64," + data.logo;

                    if(data.status == "DRAFT") this.status = "В процессе заполнения данных";
                    else if(data.status == "READY_TO_VERIFY") this.status = "На модерации";
                    else if(data.status == "APPROVED") this.status = "Проверенный";
                    else if(data.status == "NOT_APPROVED") this.status = "Не соответствующий требованиям";
                    else if(data.status == "PUBLISHED") this.status = "Проверенный";

                    // console.log(data);
                }
            });
        },
        setLogoBinary(evt) {
            this.logoBinary = evt[0];
        },
        uploadLogo(){
            if(this.logoBinary == null){
                console.log(this.logoBinary);
                alert("The dickpic mustn't be empty");
                return;
            }
            let formData = new FormData();
            formData.append('id', localStorage.company_id);
            formData.append('file', this.logoBinary);

            var query = this.$http.post( 'http://localhost:8082/company/logo',formData, // localStorage.company_id
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            ).then(function(response) {return response;})
                .catch(function(error) {return error.response;});
            query.then((response) => {
                let status = response.status;

                if(status == 409){
                    // console.log("catch error 404");
                    return;
                }

                if(status == 200){
                    alert("Logo was successfully uploaded");
                }
            });
        }
    }
}
</script>

<style lang="scss" scoped>

</style>


<!--<md-field>-->
<!--<label>Only images</label>-->
<!--<md-file v-model="single" accept="image/*"/>-->
<!--</md-field>-->