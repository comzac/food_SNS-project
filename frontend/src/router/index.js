import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/user/Login.vue";
import Signup from "../views/user/Signup.vue";
import PasswordChoice from "../views/user/PasswordChoice.vue";
import PasswordChoiceEmail from "../views/user/PasswordChoiceEmail.vue";
import PasswordChoiceEmailVerification from "../views/user/PasswordChoiceEmailVerification.vue";
import PasswordChange from "../views/user/PasswordChange.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/user/login",
    name: "Login",
    component: Login
  },
  {
    path: "/user/signup",
    name: "Signup",
    component: Signup
  },
  {
    path: "/user/password_choice",
    name: "PasswordChoice",
    component: PasswordChoice
  },
  {
    path: "/user/password_choice_email",
    name: "PasswordChoiceEmail",
    component: PasswordChoiceEmail
  },
  {
    path: "/user/password_choice_email_verification",
    name: "PasswordChoiceEmailVerification",
    component: PasswordChoiceEmailVerification
  },
  {
    path: "/user/password_change",
    name: "PasswordChange",
    component: PasswordChange
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
