<template>
    <div class="file-upload">
        <input
            style="display: none"
            type="file"
            @change="onFileSelected"
            ref="fileInput">
        <button @click="$refs.fileInput.click()">Pick File</button>
        <button @click="onUpload">Upload</button>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "fileUpload",
    data() {
        return {
            selectedFile: null
        }
    },
    methods: {
        onFileSelected(event) {
            console.log(event)
            this.selectedFile = event.target.file[0]
        },
        onUplad() {
            const fd = new FormData();
            fd.append("file", this.selectedFile, this.selectedFile.name)
            // TODO : Add path to DB
            axios.post("", fd, {
                onUploadProgress: uploadEvent => {
                    console.log('Upload Progress: ' + Math.round(uploadEvent.loaded / uploadEvent.total * 100) + '%')
                }
            })
                .then(res => {
                    console.log(res)
                });
        }
    }
}
</script>

<style scoped>

</style>