import Vue from "vue";
import VueRouter from "vue-router";

import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import PasswordChoice from "../views/user/PasswordChoice.vue";
import PasswordChoiceEmail from "../views/user/PasswordChoiceEmail.vue";
import UserDetailView from "@/views/user/UserDetailView";

import Home from "../views/Home.vue";
// import FeedItem2 from "../views/FeedItem2.vue";

import NotFoundComponent from "../components/NotFoundComponent.vue";
import ErrorComponent from "../components/ErrorComponent.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/error", name: "Error", component: ErrorComponent },
  { path: "/login", name: "Login", component: LoginView },
  { path: "/signup", name: "Signup", component: SignupView },
  { path: "*", component: NotFoundComponent },
  {
    path: "/user/password_choice",
    name: "PasswordChoice",
    component: PasswordChoice,
  },
  {
    path: "/user/password_choice_email",
    name: "PasswordChoiceEmail",
    component: PasswordChoiceEmail,
  },
  {
    path: "/userdetail", // 백에서 데이터 받아오면 이거로 변경 path: '/:username',
    name: "UserDetail",
    component: UserDetailView,
  },
  // {
  //   path: "/feed/:fid",
  //   name: "FeedItem2",
  //   component: FeedItem2,
  // },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  console.log("to : ", to);
  console.log("from : ", from);

  next();
  // const pages = [
  //   'Signup', 'Login', 'Home'
  // ]

  // const needNotAuthPages = ['Signup', 'Login']

  // const authRequired = !publicPages.includes(to.name)
  // const unauthRequired = needNotAuthPages.includes(to.name)
});

export default router;
