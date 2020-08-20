<template>
  <div>
    <v-container fill-height>
      <v-row class="text-center" align="center" justify="center">
        <v-col cols="12">
          <v-card flat class="mx-auto" max-width="614">
            <v-text-field
              label="제목"
              outlined
              solo
              name="title"
              type="text"
              v-model="feed.title"
              color="#424242"
              :messages="feed.title ? '' : '제목을 입력해주세요'"
              autocapitalize="off"
              autocorrect="off"
              autocomplete="off"
            ></v-text-field>

            <v-spacer></v-spacer>

            <v-textarea
              color="#424242"
              v-model="feed.content"
              auto-grow
              label="내용"
              outlined
              solo
              :messages="feed.content ? '' : '내용을 입력하세요'"
              single-line
              autocomplete="off"
            ></v-textarea>
            <v-spacer> </v-spacer>

            <!-- 비디오, 사진 미디어로 한번에 처리 ?? -->
            <v-window v-if="!isUpdatePage" v-model="i2" continuous>
              <v-window-item v-if="fileData == ''">
                <v-img
                  :src="require('@/assets/bees/bee.png')"
                  width="100%"
                  :aspect-ratio="1"
                  contain
                  class="grey lighten-2"
                  style="opacity: 0.6;"
                  @click="showInput()"
                ></v-img>
              </v-window-item>
              <v-window-item v-for="(preview, i) in previews" :key="i">
                <v-responsive
                  v-if="preview.includes('data:video/mp4', 0)"
                  class="align-center"
                  aspect-ratio="1"
                  style="background-color:#e0e0e0;"
                >
                  <video
                    :id="i"
                    :src="preview"
                    type="video/mp4"
                    width="100%"
                    class="my-auto"
                    autoplay
                    loop
                    muted
                    playsinline
                  ></video>
                </v-responsive>
                <v-img
                  v-else-if="preview.includes('data:image/gif', 0)"
                  :src="preview"
                  width="100%"
                  :aspect-ratio="1"
                  contain
                  class="grey lighten-2"
                ></v-img>
                <v-responsive
                  v-else
                  class="align-center"
                  aspect-ratio="1"
                  style="background-color:#e0e0e0;"
                >
                  <Croppers
                    :imgSrc="preview"
                    :profile="false"
                    @set-number="number = i"
                    @set-data="addCoordi"
                  />
                </v-responsive>
              </v-window-item>
            </v-window>

            <v-card-actions v-if="!isUpdatePage" class="justify-space-between">
              <v-btn text @click="prev" color="#ea907a">
                <v-icon>mdi-chevron-double-left</v-icon>
              </v-btn>
              <v-item-group v-model="i2" class="text-center" mandatory>
                <v-item
                  v-for="n in previews.length"
                  :key="n"
                  v-slot:default="{ active, toggle }"
                >
                  <v-btn
                    :input-value="active"
                    icon
                    @click="toggle"
                    color="#ea907a"
                  >
                    <v-icon>mdi-record</v-icon>
                  </v-btn>
                </v-item>
              </v-item-group>
              <v-btn text @click="next" color="#ea907a">
                <v-icon>mdi-chevron-double-right</v-icon>
              </v-btn>
            </v-card-actions>

            <!-- 사진 비디오 입력 -->
            <v-file-input
              ref="inputfile"
              multiple
              v-if="!isUpdatePage"
              v-model="fileData"
              prepend-icon
              accept=".png, .jp(e)g, .gif, .jfif, .mp4"
              outlined
              solo
              label="사진 또는 동영상 선택"
              @change="previewImage"
              color="#424242"
              messages="20mb 이하 .png, jp(e)g, gif, jfif .mp4 파일만 최대 3개 업로드 됩니다."
            ></v-file-input>
            <v-spacer>
              <br />
            </v-spacer>
            <div>
              <v-btn
                @click="$router.go(-1)"
                class="white--text"
                color="grey"
                width="99"
                >취소</v-btn
              >
              <v-divider class="mr-5" vertical></v-divider>
              <!-- 클릭하면 피드 상세 페이지로 -->
              <v-btn
                v-if="isUpdatePage"
                :disabled="!feed.title || !feed.content || !fileData"
                @click="updateFeedByFormData()"
                color="#ea907a"
                class="white--text"
                >수정 완료</v-btn
              >
              <v-btn
                v-else
                :disabled="!feed.title || !feed.content || !fileData.length"
                @click="insertFeedByFormData(), (overlay = true)"
                color="#ea907a"
                class="white--text"
                >작성 완료</v-btn
              >
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </div>
</template>

<script>
import swal from "sweetalert";

import Croppers from "@/components/feed/item/Croppers";

import { mapState, mapActions } from "vuex";

export default {
  name: "FeedCreateView",
  components: { Croppers },
  computed: {
    ...mapState("contests", ["selectedContestFeed"]),
    isUpdatePage() {
      return !!this.$route.params.fid;
    },
  },
  data() {
    return {
      number: null,
      overlay: false,
      i2: 0,
      feedData: {},
      previews: [],
      imageData: "",
      fileData: [],
      feed: {
        title: "",
        content: "",
      },
      video: "",
    };
  },
  methods: {
    showInput() {
      this.$refs.inputfile.$refs.input.click();
    },
    addCoordi(data) {
      // console.log("coordi", data);
      this.fileData[this.number].coordi = data
        .replace(/ /g, "")
        .replace(/\r/g, "")
        .replace(/\n/g, "");
      // console.log(this.fileData);
      this.number = null;
    },
    ...mapActions("contests", [
      "insertContestFeed",
      "updateContestFeed",
      "getContestFeedDetail",
    ]),
    next() {
      this.i2 = this.i2 + 1 === this.previews.length ? 0 : this.i2 + 1;
    },
    prev() {
      this.i2 = this.i2 - 1 < 0 ? this.previews.length - 1 : this.i2 - 1;
    },
    previewImage(file) {
      this.overlay = true;
      this.previews = [];
      this.fileData = [];
      this.i2 = 0;
      // console.log(file);
      if (file.length > 3) {
        // console.log(file.length);
        swal({
          title: "파일 개수 초과",
          text: "파일이 3개보다 많이 선택되었습니다.",
          icon: "warning",
          dangerMode: true,
          buttons: [null, "확인"],
        });
        this.overlay = false;
        return false;
      }
      for (let i = 0; i < file.length; i++) {
        if (file[i].size > 20 * 1024 * 1024) {
          swal({
            title: "파일 용량 초과",
            text: "20mb보다 큰 파일이 선택되었습니다.",
            icon: "warning",
            dangerMode: true,
            buttons: [null, "확인"],
          });
          this.overlay = false;
          return false;
        } else if (
          !/.mp4/.test(file[i].name) &&
          !/.png/.test(file[i].name) &&
          !/.gif/.test(file[i].name) &&
          !/.jfif/.test(file[i].name) &&
          !/.jpg/.test(file[i].name) &&
          !/.jpeg/.test(file[i].name)
        ) {
          swal({
            title: "파일 형식 문제",
            text: "파일 형식이 어긋납니다.",
            icon: "error",
            dangerMode: true,
            buttons: [null, "확인"],
          });
          this.overlay = false;
          return false;
        } else {
          // console.log(file[i]);
          this.fileData.push(file[i]);
        }
      }
      for (let i = 0; i < this.fileData.length; i++) {
        let reader = new FileReader();
        reader.onload = () => {
          this.fileData[i].src = reader.result;
          this.previews.push(reader.result);
        };
        reader.readAsDataURL(this.fileData[i]);
      }
      // console.log(this.previews);
      // console.log(this.fileData);
      window.scrollTo(0, 0);
      this.overlay = false;
      swal("미디어 파일이 업로드되었습니다.\n사진 파일은 수정이 가능합니다.", {
        buttons: [null, "확인"],
      });
    },
    insertFeedByFormData() {
      this.feedData.feed = this.feed;
      this.feedData.dbFiles = this.fileData;
      this.insertContestFeed(this.feedData);
    },

    updateFeedByFormData() {
      this.feedData.feed = this.feed;
      this.feedData.id = this.$route.params.fid;
      this.updateContestFeed(this.feedData);
    },

    initData() {
      this.feed.title = this.selectedContestFeed.contestFeed.title;
      this.feed.content = this.selectedContestFeed.contestFeed.content;
      // this.fileData = this.selectedFeed.dbFiles;
      // this.feedhashtag = this.selectedFeed.hashtag;
    },
  },

  created() {
    if (this.$route.params.fid) {
      this.getContestFeedDetail(this.$route.params.fid).then(this.initData());
    }
  },
  mounted() {
    window.scrollTo(0, 0);
  },
  //   mounted() {
  //     this.$emit("change-page", 7);
  //   },
};
</script>

<style></style>
