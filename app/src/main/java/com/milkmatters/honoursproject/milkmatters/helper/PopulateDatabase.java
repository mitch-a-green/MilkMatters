package com.milkmatters.honoursproject.milkmatters.helper;

import android.content.Context;
import android.location.Location;

import com.milkmatters.honoursproject.milkmatters.database.BecomeDonorTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.DepotsTableHelper;
import com.milkmatters.honoursproject.milkmatters.database.FeedTableHelper;
import com.milkmatters.honoursproject.milkmatters.model.Depot;
import com.milkmatters.honoursproject.milkmatters.model.EventItem;
import com.milkmatters.honoursproject.milkmatters.model.NewsItem;
import com.milkmatters.honoursproject.milkmatters.model.Question;

/**
 * Class to automatically populate the database with information.
 * Used when the application is first run.
 */
public class PopulateDatabase {
    private Context context; // used to interface with the database

    /**
     * Constructor for the PopulateDatabase class.
     * @param context the context to be used when interfacing with the database
     */
    public PopulateDatabase(Context context) {
        this.context = context;
        this.addDepots();
        this.addFeedItems();
        this.addQuestions();
    }

    public void addDepots()
    {
        DepotsTableHelper depotsTableHelper = new DepotsTableHelper(this.context);
        Location location1 = new Location("");
        location1.setLatitude(-33.949444);
        location1.setLongitude(18.475001);
        Location location2 = new Location("");
        location2.setLatitude(-34.07443000000001);
        location2.setLongitude(18.874040000000036);
        Location location3 = new Location("");
        location3.setLatitude(-34.08094);
        location3.setLongitude(18.844839999999976);
        Location location4 = new Location("");
        location4.setLatitude(-33.8773638);
        location4.setLongitude(18.57691180000006);
        Location location5 = new Location("");
        location5.setLatitude(-33.9746808);
        location5.setLongitude(18.506525199999942);
        Location location6 = new Location("");
        location6.setLatitude(-33.943348);
        location6.setLongitude(18.859915900000033);
        Location location7 = new Location("");
        location7.setLatitude(-33.901919);
        location7.setLongitude(18.39977399999998);
        Location location8 = new Location("");
        location8.setLatitude(-33.8584538);
        location8.setLongitude(18.68158649999998);
        Location location9 = new Location("");
        location9.setLatitude(-33.9138399);
        location9.setLongitude(18.589034800000036);
        Location location10 = new Location("");
        location10.setLatitude(-34.08626739999999);
        location10.setLongitude(18.45726190000005);
        Location location11 = new Location("");
        location11.setLatitude(-34.1241908);
        location11.setLongitude(18.387825799999973);
        Location location12 = new Location("");
        location12.setLatitude(-34.4191774);
        location12.setLongitude(19.242817899999977);
        Location location13 = new Location("");
        location13.setLatitude(-33.8155058);
        location13.setLongitude(18.49359030000005);
        Depot depot1 = new Depot("Milk Matters Headquarters", "08:00 - 16:00 Mon - Fri",
                "", "021 659 5599", "Room 37 Mowbray Maternity Hosp, Hornsey Road, Mowbray", location1);
        Depot depot2 = new Depot("Somerset West",
                "Available any time", "Irene Bourquin",
                "021 852 3040 / 082 897 0377", "22 Dromedaris Rd, Somerset West", location2);
        Depot depot3 = new Depot("The Wellness Pod",
                "09:00 - 16:00 Mon to Fri", "Julia",
                "none", "32 Bright Street, Somerset West", location3);
        Depot depot4 = new Depot("Panorama Breastfeeding Clinic",
                "08:00 Mon to Fri (No consultation appointment necessary)", "Anelle",
                "021 939 9720 / 083 703 7711", "14 Dorp Street, Panorama", location4);
        Depot depot5 = new Depot("Al-Nisa Maternity Home",
                "07:30 – 17:00 Mon – Fri & 09:00 – 13:00 Sat", "Cathy or Amina",
                "021 696 8892", "7 Rokeby Road, Crawford / Rondebosch East", location5);
        Depot depot6 = new Depot("Stellenbosch Breastfeeding Clinic",
                "09:00 to 17:00 Mon - Fri", "Erica Neser",
                "083 292 5252", "10 Ackermann Street, Stellenbosch", location6);
        Depot depot7 = new Depot("Thula Baby Centre",
                "09:00 - 12:00 Mon to Fri", "Heather",
                "021 434 2614", "South Seas, 129 Beach Rd, Mouille Point", location7);
        Depot depot8 = new Depot("Clicks, Glengarry Village Centre",
                "09:00 – 17:00 Mon to Fri", "Sr Lizbe Egnelbrecht ",
                "021 981 2160", "De Bron Road, Brackenfell", location8);
        Depot depot9 = new Depot("Kids Medi Care",
                "None listed", "Dr Rahmat Bagus (MBChB)",
                "021 933 7004 / 082 826 4600", "18 Phillips Street, Parow Valley", location9);
        Depot depot10 = new Depot("Lakeside Pharmacy",
                "08:30 - 19:00 Mon, 08:30  - 15:30 Tue, Thu, & Fri, 08:30 - 13:00 Sat", "Julia Noble",
                "021 788 6300", "62 Main Road, Lakeside", location10);
        Depot depot11 = new Depot("Sun Valley Pharmacy",
                "09:00 - 19:00 Mon to Fri, 09:00 - 17:00 Sat, 09:00 - 14:00 Sun", "Sr Bergit de Villiers",
                "021 785 3470", "Shop G48, Longbeach Mall, Noordhoek", location11);
        Depot depot12 = new Depot("Hermanus Pharmacy Clinic",
                "09:00 - 15:00 Mon to Fri", "Sr Suzaan Di Giannatale",
                "071 656 7090", "145 Main Road, Hermanus", location12);
        Depot depot13 = new Depot("Blaauwberg Mother & Child Clinic",
                "08:00 - 18:00 Mon to Fri, 08:00 - 16:00 Sat & Sun", "Dawn Collier & Mandy Richards",
                "021 521 9000", "Cnr. Link Road and Park Drive, Parklands", location13);
        depotsTableHelper.createDepot(depot1);
        depotsTableHelper.createDepot(depot2);
        depotsTableHelper.createDepot(depot3);
        depotsTableHelper.createDepot(depot4);
        depotsTableHelper.createDepot(depot5);
        depotsTableHelper.createDepot(depot6);
        depotsTableHelper.createDepot(depot7);
        depotsTableHelper.createDepot(depot8);
        depotsTableHelper.createDepot(depot9);
        depotsTableHelper.createDepot(depot10);
        depotsTableHelper.createDepot(depot11);
        depotsTableHelper.createDepot(depot12);
        depotsTableHelper.createDepot(depot13);
        depotsTableHelper.closeDB();
    }

    /**
     * Method to add the feed items to the database
     */
    public void addFeedItems()
    {
        FeedTableHelper feedTableHelper = new FeedTableHelper(this.context);
        NewsItem newsItem1 = new NewsItem("Milk Needed", "We need milk due to high demand.",
                "08-06-2016", "www.milkmatters.org");
        NewsItem newsItem3 = new NewsItem("Incredible Mom-And-Baby Photos Show The Beauty Of The ‘Breast Crawl’", "When Jessica, 32, sat down to make her birth plan before her daughter’s birth last May, she had one priority (in addition, of course, to everyone’s health and safety): The “breast crawl.”\n" +
                "\n" +
                "Newborns are hardwired to breastfeed, and the breast crawl is the practice of letting them use those instincts to slowly inch their way to their mother’s nipple to nurse for the very first time. Immediately after delivery, the baby is placed on the mother’s abdomen and given time to scoot up to her breasts, sniffing along the way. (BreastCrawl.org says says it generally takes between 30 and 60 minutes, after which babies who haven’t arrived at the breast yet may need a little help.) Proponents say that giving babies that first hour of life to make the journey helps promote early initiation of breastfeeding ― which has clear benefits ― and eases moms and babies through one of life’s biggest transitions. \n" +
                "\n" +
                "“It’s not something I’nutrition_new heard about before,” says Jessica, who went to a practice with two midwives and two OB-GYNs ― all of whom were supportive of the idea. “When I read about it, I thought it sounded magical.” \n" +
                "\n" +
                "Fortunately, Jessica and her husband hired Austin-based breastfeeding and birth photographer Leilani Rogers to document the birth, and she captured the crawl inch-by-inch. “To witness the connection baby made with mom over the span of that 48 minutes was fascinating,” Rogers said. “She didn’t fuss, just slowly but surely made her way to the nipple and latched on once she was in just the right position.”\n" +
                "\n" +
                "Magical, indeed!",
                "19-08-2016", "http://www.huffingtonpost.com/entry/incredible-mom-and-baby-photos-show-the-beauty-of-the-breast-crawl_us_57b73741e4b03d5136881944");
        NewsItem newsItem4 = new NewsItem("Breast-Fed Babies May Have Longer Telomeres, Tied to Longevity", "Breast-fed babies have healthier immune systems, score higher on I.Q. tests and may be less prone to obesity than other babies.\n" +
                "\n" +
                "Now new research reveals another possible difference in breast-fed babies: They may have longer telomeres.\n" +
                "\n" +
                "Telomeres are stretches of DNA that cap the ends of chromosomes and protect the genes from damage. They’re often compared to the plastic tips at the end of shoelaces that prevent laces from unraveling. Telomeres shorten as cells divide and as people age, and shorter telomeres in adulthood are associated with chronic diseases like diabetes. Some studies have linked longer telomeres to longevity.\n" +
                "\n" +
                "The new study, published in The American Journal of Clinical Nutrition, is a hopeful one, its authors say, because it suggests telomere length in early life may be malleable. The researchers, who have been following a group of children since birth, measured the telomeres of 4- and 5-year-olds, and discovered that children who consumed only breast milk for the first four to six weeks of life had significantly longer telomeres than those who were given formula, juices, teas or sugar water.\n" +
                "\n" +
                "Drinking fruit juice every day during the toddler years and a lot of soda at age 4 was also associated with short telomeres.\n" +
                "\n" +
                "Socioeconomic differences among mothers can muddy findings about breast-feeding because the practice is more common among more educated mothers. However, this group of children was fairly homogeneous. All of them were born in San Francisco to low-income Latina mothers, most of whom qualified for a government food program.\n" +
                "\n" +
                "“This adds to the burgeoning evidence that when we make it easier for mothers to breast-feed, we make mothers and babies healthier,” said Dr. Alison M. Stuebe, an expert on breast-feeding who is the medical director of lactation services at UNC Health Care in Chapel Hill, N.C., and was not involved in the study. “The more we learn about breast milk, the more it’s clear it is pretty awesome and does a lot of cool stuff.”\n" +
                "\n" +
                "The study did not establish whether or not breast-feeding enhanced telomere length. It may be that babies born with longer telomeres are more likely to succeed at breast-feeding. A major drawback of the research was that telomere length was only measured at one point in time, when the children were 4 or 5 years old. There was no data on telomere length at birth or during the first few months of life.\n" +
                "\n" +
                "“We don’t have a baseline to see if these kids were different when they came out,” Dr. Stuebe said. “It could be that really healthy babies can latch on and feed well, and they already had longer telomeres. It could be successful breast-feeding is a sign of a more robust kid.”\n" +
                "\n" +
                "The researchers were following children who were part of the Hispanic Eating and Nutrition study, a group of 201 babies born in San Francisco to Latina mothers recruited in 2006 and 2007 while they were still pregnant. The goal of the research was to see how early life experiences, eating habits and environment influence growth and the development of cardiac and metabolic diseases as children grow.\n" +
                "\n" +
                "Researchers measured the babies’ weight and height when the children were born. At four to six weeks of age, they gathered detailed information about feeding practices, including whether the baby had breast milk and for how long, and whether other milk substitutes were used, such as formula, sugar-sweetened beverages, juices, flavored milks and waters. Information was also gathered about the mothers.\n" +
                "\n" +
                "Children were considered to have been exclusively breast-fed at 4 to 6 weeks of age if they received nothing but breast milk, as well as medicine or vitamins.\n" +
                "\n" +
                "When the children were 4 and 5 years old, researchers took blood spot samples that could be used to measure the telomeres in leukocytes, which are white blood cells, from 121 children. They found that children who were being exclusively breast-fed at 4 to 6 weeks of age had telomeres that were about 5 percent longer, or approximately 350 base pairs longer, than children who were not.\n" +
                "\n" +
                "The new findings may help explain the trove of benefits that accrue from breast-feeding, said Janet M. Wojcicki, an associate professor of pediatrics and epidemiology at the University of California, San Francisco, and the paper’s lead author.\n" +
                "\n" +
                "“What’s remarkable about breast-feeding is its ability to improve health across organ systems,” Dr. Wojcicki said. “Telomere biology is so central to the processes of aging, human health and disease, and may be the link to how breast-feeding impacts human health on so many levels.”\n" +
                "\n" +
                "There are several possible explanations for the correlation between breast-feeding and longer telomeres. Breast milk contains anti-inflammatory compounds, which may confer a protective effect on telomeres. It’s also possible that parents who exclusively breast-feed their babies are more scrupulous about a healthy diet generally.\n" +
                "\n" +
                "Yet another possibility is that breast-feeding is a proxy for the quality of mother-child attachment and bonding, said Dr. Pathik D. Wadhwa, who was not involved in the research but studies early-life determinants of health at the University of California, Irvine School of Medicine. “We know from studies looking at telomere length changes in babies who came from orphanages that the quality of the attachment and interaction, and more generally the quality of care that babies receive, plays a role in the rate of change in telomere length,” he said.\n" +
                "\n" +
                "When children are exposed to adversity, neglect or violence at an early age, “psychological stress creates a biochemical environment of elevated free radicals, inflammation and stress hormones that can be harmful to telomeres,” said Elissa Epel, one of the authors of the study who is a professor at the University of California, San Francisco, and director of the Aging, Metabolism and Emotions Lab.\n" +
                "\n" +
                "“The idea that breast-feeding may be protective for telomeres is heartening because we don’t know much about what’s going to help protect them in children, besides avoiding toxic stress. And boy, do we want to know,” Dr. Epel said.\n" +
                "\n" +
                "Although genes can’t be changed, Dr. Epel said, “This is part of the genome that appears to be at least partly under personal control.”",
                "04-08-2016", "http://well.blogs.nytimes.com/2016/08/04/breast-fed-babies-may-have-longer-telomeres-tied-to-longevity/?smid=fb-share&_r=1");
        NewsItem newsItem5 = new NewsItem("Wayde donates R500K for premature babies", "Cape Town - Born at 29 weeks and weighing just over 1kg, Wayde van Niekerk’s parents never thought he would survive, let alone become a successful athlete.\n" +
                "\n" +
                "After his mother, Odessa Swarts, gave birth to him at Tygerberg 23 years ago, the South African track and field champion and 400m sprinter would later be treated at Groote Schuur Hospital’s neonatal unit, where he spent several weeks in an incubator.\n" + "“From what my mother tells me it was apparently a very difficult and emotional journey to have a premature baby. There were days where she was not sure whether I was going to make it the next day. That’s how sick I was,” he said.\n" +
                "\n" +
                "On Thursday, Van Niekerk, currently the fourth fastest person in history and the first athlete to run the 100m in under 10 seconds, and the 200m in under 20 seconds, donated half-a-million rand to the Newborns Groote Schuur Trust to aid the upgrade of that hospital’s neonatal unit.\n" +
                "\n" +
                "Through the Trust, the hospital is fundraising money to expand the unit, which is currently battling to cope due the incidence of pre-term birth in the province.\n" +
                "\n" +
                "The unit, which was built in the 1970s, is overcrowded and unable to meet the demand of caring for more than 3 000 babies a year - mostly premature babies.\n" +
                "\n" +
                "During the handover of the cheque at the Century City Convention Centre on Thursday, where he was also named the face of an ICT company, T-systems, Van Niekerk said through the donation he hoped to make his mother proud.\n" +
                "\n" +
                "“My mother is very passionate about premature babies since she cared for one herself. Through this donation I’m showing my support to her causes, and to make her proud. I’m my mother’s seed and I want to help her in every manner I can. But more importantly, I’m hoping to help thousands of premature babies who go through Groote Schuur Hospital’s neonatal unit.\n" +
                "\n" +
                "“As a small premature baby, I was given a fighting chance to survive, and so it’s very important to me to help give other preemies the best chance at life.”\n" +
                "\n" +
                "Dr Lloyd Tooke, senior neonatologist at Groote Schuur Hospital, said Van Niekerk’s success as an athlete had shown that premature babies could also achieve against the odds. “He is truly an inspiration not only for families of premature infants but for all of us.”\n" +
                "\n" +
                "Having raised more than R5 million so far, the hospital’s chief executive Bhavna Patel said the latest amount would be used to buy modern equipment for the upgraded unit.",
                "20-05-2016", "http://www.iol.co.za/sport/athletics/pics-wayde-donates-r500k-for-premature-babies-2023890");
        NewsItem newsItem6 = new NewsItem("Aden finds out more about a breast milk bank called Milk Matters #IHeartBreakfast", "Aden Thomas chats to Jenny Wright from an organisation called Milk Matters. They're a community-based breast milk bank that pasteurises and distributes donations of screened breast milk from healthy donors to premature, ill and vulnerable babies whose own mothers cannot supply the breast milk to meet their baby’s needs. Milk Matters also facilitates the setting up of milk banks in health institutions.",
                "01-09-2016", "https://soundcloud.com/heartbreakfast-1/aden-finds-out-more-about-a-breast-milk-bank-called-milk-matters-heartbreakfast");
        EventItem eventItem1 = new EventItem("Milk Matters Workshop", "Two honours students will be demonstrating the Milk Matters App",
                "07-06-2016", "www.milkmatters.org", "14-09-2016", "10:00", "Rustenburg Square");
        EventItem eventItem2 = new EventItem("March for Milk", "We still have spaces left for this years March for Milk!!\nCome and enjoy a 2.5km or 5km walk / run for the whole family in aid of Milk Matters, the Breastmilk Bank.\nHelp raise funds and awareness and ensure more babies have access to life-saving donor milk.",
                "02-03-2016", "https://www.facebook.com/events/696954203740860/?active_tab=highlights", "12-03-2016", "9:00", "Sea Point Beach Front");
        feedTableHelper.createNewsItem(newsItem1);
        feedTableHelper.createEventItem(eventItem1);
        feedTableHelper.createNewsItem(newsItem3);
        feedTableHelper.createNewsItem(newsItem4);
        feedTableHelper.createNewsItem(newsItem5);
        feedTableHelper.createNewsItem(newsItem6);
        feedTableHelper.createEventItem(eventItem2);
        feedTableHelper.closeDB();
    }

    /**
     * Method to add the quiz questions to the database.
     */
    public void addQuestions()
    {
        BecomeDonorTableHelper becomeDonorTableHelper =
                new BecomeDonorTableHelper(this.context);
        Question q1=new Question("Would you be willing to complete a donor screening form?","Yes", "As long as it isn't too long", "No", "No");
        becomeDonorTableHelper.addQuestion(q1);
        Question q2=new Question("Would you be willing to do a HIV and Hepatitis B blood test?", "Yes", "As long as it is for free", "No", "No");
        becomeDonorTableHelper.addQuestion(q2);
        Question q3=new Question("Are you able to collect sterile jars for milk from Milk Matters?","Yes", "Depends on how often","No","No");
        becomeDonorTableHelper.addQuestion(q3);
        Question q4=new Question("Do you have storage facilities to freeze your milk at home?",	"Yes", "I can make a plan", "No", "No");
        becomeDonorTableHelper.addQuestion(q4);
        Question q5=new Question("Are you able to drop off your donations at a depot location?","Yes","It will be challenging, but I'll try", "No","No");
        becomeDonorTableHelper.addQuestion(q5);
        becomeDonorTableHelper.closeDB();
    }
}
