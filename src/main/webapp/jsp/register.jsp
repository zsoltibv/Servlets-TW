
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Tailwindcss import via cdn -->
    <script src="https://cdn.tailwindcss.com"></script> <!-- This script must be in format <script></script> and not the short form <script/>-->
    <!-- You can also Bootstrap, Foundation, MaterialUI etc. -->
</head>
<body>
    <div class="w-screen h-screen flex justify-center items-center
            bg-gradient-to-br from-purple-700 to-amber-700">
        <div class="p-10 bg-white rounded-xl drop-shadow-lg space-y-5" >
            <form  action="/register" method="POST">
                <h1 class="text-center text-3xl">REGISTER</h1>
                <div class="flex flex-col space-y-2">
                    <label class="text-sm font-light" for="email">Email</label>
                    <input class="w-96 px-3 py-2 rounded-md border border-slate-400" type="email" placeholder="Your Email"
                           name="email" id="email">
                </div>
                <div class="flex flex-col space-y-2">
                    <label class="text-sm font-light" for="password">Password</label>
                    <input class="w-96 px-3 py-2 rounded-md border border-slate-400" type="password"
                           placeholder="Your Password" name="password" id="password">
                </div>
                <div class="flex flex-col space-y-2">
                    <label class="text-sm font-light" for="password">Repeat The Password</label>
                    <input class="w-96 px-3 py-2 rounded-md border border-slate-400" type="password"
                           placeholder="Repeat Password" name="repeat_password" id="repeat_password">
                </div>

                <button class="w-full px-10 py-2 bg-blue-600 text-white rounded-md space-y-2
                        hover:bg-blue-500 hover:drop-shadow-md duration-300 ease-in" type="submit">
                    Register
                </button>

                <div class="flex items-center space-x-2">
                    <input type="checkbox" name="isTeacher" id="isTeacher" class="text-blue-500" />
                    <label class="text-sm font-light" for="isTeacher">Are you a teacher?</label>
                </div>
            </form>

            <form action="/login" method="GET">
                <button class="w-full bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4
                        border border-blue-500 hover:border-transparent rounded duration-300 ease-in" type="submit">
                    Sign In
                </button>
            </form>
        </div>
    </div>
</body>
</html>
