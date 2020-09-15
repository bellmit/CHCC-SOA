<template>
	<div>
          <el-upload
            class="uploadHide"
            :action="uploadPath"
            name="file"
            :show-file-list="false"
            :on-success="editorSuccess"
           >
          </el-upload>

       <quill-editor
            v-model="editorCont"
            ref="myQuillEditor"
            :options="editorOption"
						@change="geteditor($event)"
        >
        </quill-editor>
	</div>
</template>

<script>
  import {upload_path} from '@/api/api'
  // 工具栏配置
  const toolbarOptions = [
    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
    ['blockquote', 'code-block'],

    [{'header': 1}, {'header': 2}],               // custom button values
    [{'list': 'ordered'}, {'list': 'bullet'}],
    [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
    [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
    [{'direction': 'rtl'}],                         // text direction

    [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
    [{'header': [1, 2, 3, 4, 5, 6, false]}],

    [{'color': []}, {'background': []}],          // dropdown with defaults from theme
    [{'font': []}],
    [{'align': []}],
    ['link', 'image', 'video'],
    ['clean']                                         // remove formatting button
  ]
  export default {
    props:['menpiaoeditor' ],
    data(){
      const self = this;
      return {
        editorOption: {
          modules: {
              toolbar: {
                  container: toolbarOptions,  // 工具栏
                  handlers: {
                      'image': function (value) {
                          if (value) {
                              document.querySelector('.uploadHide input').click()
                          } else {
                              this.quill.format('image', false);
                          }
                      }
                  }
              }
          }
        },
        uploadPath: upload_path,
				editorCont:''
      }
    },
    watch: {
      menpiaoeditor(val) {  
        this.editorCont = val;
      }
    },
    mounted(){

    },
    created(){
       this.editorCont = this.menpiaoeditor
    },
    methods: {
			geteditor () {
			  this.$emit('do-editor',this.editorCont)
			},
      //富文本图片上传
      editorSuccess (response, file) {
        // response为图片服务器返回的数据
        try {
          // 获取富文本组件实例
          let quill = this.$refs.myQuillEditor.quill
          // 如果上传成功
          if (response.status == '10000') {
            // 获取光标所在位置
            let length = quill.getSelection().index
            // 插入图片  res.info为服务器返回的图片地址
            quill.insertEmbed(length, 'image',response.data)
            // 调整光标到最后
            quill.setSelection(length + 1)
          } else {
            this.$message.error('图片插入失败')
          }
        } catch (e) {
          this.$message.error('图片插入失败')
        } finally {
          // loading动画消失
          this.quillUpdateImg = false
        }
      }
    }
  }
</script>

<style scoped>
	.uploadHide{
	display:none;
	}
  .quill-editor {
  height: 600px;
  padding-bottom:42px
  }
</style>
