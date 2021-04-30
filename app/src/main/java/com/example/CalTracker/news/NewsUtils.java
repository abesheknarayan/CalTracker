package com.example.CalTracker.news;

import android.content.Context;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.example.CalTracker.R;

public class NewsUtils {
    public static ArrayList<NewsBean> getAllNews(Context context) {
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            NewsBean newsBean = new NewsBean();
            newsBean.title = "Lessons from a diabetes clinic in Malawi: why everyone should follow a healthy diet";
            newsBean.des = "Diabetes mellitus is common in Malawi: over 268,000 adults live with the disease, and the " +
                    "number is expected to double in the next 20 years. It’s a noncommunicable disease which occurs when " +
                    "the body can’t turn food into energy properly.";
            newsBean.icon = "https://images.theconversation.com/files/352956/original/file-20200814-20-o3shht.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=926&fit=clip";
            newsBean.news_url = "https://theconversation.com/lessons-from-a-diabetes-clinic-in-malawi-why-everyone-should-follow-a-healthy-diet-143909";
            arrayList.add(newsBean);

            NewsBean newsBean1 = new NewsBean();
            newsBean1.title = "Post Covid-19 care: Nutritional guidelines for those recovering from coronavirus";
            newsBean1.des = "The best hope for ending the pandemic isn't to choose between masks, social distancing, and vaccines, but to combine the three approaches to work as a team";
            newsBean1.icon = "https://images.indianexpress.com/2021/04/nutrition_1200_pixabay.jpg";
            newsBean1.news_url = "https://indianexpress.com/article/lifestyle/health/nutritional-guidelines-post-covid-patients-pandemic-7283069/";
            arrayList.add(newsBean1);

            NewsBean newsBean2 = new NewsBean();
            newsBean2.title = "How much food should my child be eating? And how can I get them to eat more healthily?";
            newsBean2.des = "Children need healthy food in the right amount so they get all the nutrients needed to grow, learn and thrive.";
            newsBean2.icon = "https://images.theconversation.com/files/318199/original/file-20200303-18295-1hx3dv.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=754&h=502&fit=crop&dpr=1";
            newsBean2.news_url = "https://theconversation.com/how-much-food-should-my-child-be-eating-and-how-can-i-get-them-to-eat-more-healthily-130470";
            arrayList.add(newsBean2);

            NewsBean newsBean3 = new NewsBean();
            newsBean3.title = "I am Covid positive in home-isolation. What to eat? Dietitians answer";
            newsBean3.des = "If you are suffering from Covid-19 and are in home-isolation, it is imperative that you follow a balanced diet apart from consuming prescribed medicines in order to recover soon. Weakness is one of the major side-effects of battling the deadly virus and the loss of taste and smell often forces patients to refrain from eating on time.";
            newsBean3.icon = "https://akm-img-a-in.tosshub.com/indiatoday/images/story/202104/GettyImages-1281059015_1200x768.jpeg?sVfgxuq6_jxZJyWy137b8ndMAGh5q3eX&size=770:433";
            newsBean3.news_url = "https://theconversation.com/hormone-diets-are-all-the-rage-but-do-they-actually-work-122744";
            arrayList.add(newsBean3);

            NewsBean newsBean4 = new NewsBean();
            newsBean4.title = "World Health Day: Five Priorities for Solving India’s (Mal)nutrition Conundrum";
            newsBean4.des = "In nation-building, the most important, yet (unfortunately) most misunderstood and often neglected topic is that of public health and nutrition (PHN). Everything seems to affect nutrition—our personal attributes like stress, sleep, digestion issues; or environmental maladies like pollution, climate change. Even the timing of our meals and accompanying foods impact the expected nourishment. For instance, the tempting masala milk chai after your meals is quite a bummer as it renders the iron in your food useless.";
            newsBean4.news_url = "https://weather.com/en-IN/india/health/news/2021-04-07-world-health-day-need-to-solve-india-malnutrition-conundrum";
            newsBean4.icon = "https://s.w-x.co/util/image/w/TWC%20WHD%20T2.jpg?crop=16:9&width=980&format=pjpg&auto=webp&quality=60";
            arrayList.add(newsBean4);

            NewsBean newsBean5 = new NewsBean();
            newsBean5.title = "Must-have immunity boosters in your daily nutrition";
            newsBean5.des = "The healthcare structure is under immense pressure; there is a shortage of beds, oxygen, and vaccines. \"While the government is trying to do its best, we as individuals can't shy away from our duties. We can do the best during this time to work on our immunity, eat healthy, and follow all protocols. The best protection against infectious diseases and a secret to good health is a strong immune system. A good resistance to infections can be established by improving the body's natural ability to protect itself,\" says Ryan Fernando - Founder QUA Nutrition. \"Low immunity is caused by a variety of factors, including poor nutrition, and some foods and supplements may help improve immunity,\" adds the celebrity nutritionist.";
            newsBean5.icon = "https://static.toiimg.com/thumb/msid-82215811,width-800,height-600,resizemode-75,imgsize-142547,pt-32,y_pad-40/82215811.jpg";
            newsBean5.news_url = "https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/must-have-immunity-boosters-in-your-daily-nutrition/articleshow/82215811.cms";
            arrayList.add(newsBean5);

            NewsBean newsBean6 = new NewsBean();
            newsBean6.title = "Diet plan and food dos and don'ts for COVID-19 patients";
            newsBean6.des = "Nutrition plays a significant role for both, the COVID-19 patients and those who are on the path of recovery. During COVID-19, the body gets weakened and it continues for days even after recovering from the symptoms. Thus, it becomes essential to consume the right kind of diet for a speedy and complete recovery of the body. We spoke to celebrity nutritionist Sandhya Gugnani and she recommended some food and diet tips based on the latest research.";
            newsBean6.icon = "https://static.toiimg.com/thumb/msid-82213652,imgsize-216660,width-800,height-600,resizemode-75/82213652.jpg";
            newsBean6.news_url = "https://timesofindia.indiatimes.com/life-style/food-news/diet-plan-and-food-dos-and-donts-for-covid-19-patients/articleshow/82213682.cms";
            arrayList.add(newsBean6);

            NewsBean newsBean7 = new NewsBean();
            newsBean7.title = "Are you Vitamin C deficient? Eat these 5 foods daily to build immunity";
            newsBean7.des = "With Covid cases rapidly increasing in the country, it has become very important to build a strong immune system. Other than getting vaccinated, everyone should focus on boosting their immunity to prevent the infection. Vitamin C is important in the process of strengthening immunity.\n" +
                    "\n" +
                    "Various methods are adopted by different people to strengthen the immune system of the body, but you should definitely include these foods rich in vitamin C in your diet. This will boost your immunity and prevent many dangerous diseases.";
            newsBean7.icon = "https://resize.indiatvnews.com/en/resize/newbucket/715_-/2021/04/vitamin-c-1619503368.jpg";
            newsBean7.news_url = "https://www.indiatvnews.com/health/are-you-vitamin-c-deficient-eat-these-5-foods-daily-to-build-immunity-700766";
            arrayList.add(newsBean7);
        }
        Collections.shuffle(arrayList);


        return arrayList;
    }
}
