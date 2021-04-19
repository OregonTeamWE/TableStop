package com.example.tableStop

import com.example.tableStop.viewModel.BlogPost

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<BlogPost>{
            val list = ArrayList<BlogPost>()
            list.add(
                BlogPost(
                    "DUNGEONS & DRAGONS",
                    "This is a fantasy role-playing game",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/D%26D_IMAGE_2.png",
                    "@Wizards_DnD",
                    "https://twitter.com/wizards_dnd?lang=en"
                )
            )
            list.add(
                BlogPost(
                    "15 tabletop gaming you should know",
                    "Polygon ",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/POLYGON_IMAGE.png",
                    "Will Williams",
                    "https://www.polygon.com/podcasts/2018/9/26/17860176/best-dungeons-dragons-dd-podcasts-tabletop-gaming"
                )
            )

            list.add(
                BlogPost(
                    "Tabletop RPGs",
                    "Twitch",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/TWITCH_IMAGE3.png",
                    "RPG",
                    "https://www.twitch.tv/directory/game/Tabletop%20RPGs"
                )
            )
            list.add(
                BlogPost(
                    "Youtube",
                    "Game video",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/YOUTUEB_IMAGE.png",
                    "Dicebreaker",
                    "https://www.youtube.com/channel/UC0jMXccxfvnwXvTpngFwbNA"
                )
            )
            list.add(
                BlogPost(
                    "GEEK&SUNDRY",
                    "TABLETOP",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/TABLETOP_IMAGE.jpg",
                    "TABLETOP",
                    "https://geekandsundry.com/shows/tabletop/"
                )
            )
            list.add(
                BlogPost(
                    "17 best Tabletop Simulator mods for popular board games",
                    "17 best Tabletop Simulator mods for popular board games",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/BOARD_GAME_IMAGE.png",
                    "Matt Jarvis",
                    "https://www.dicebreaker.com/topics/tabletop-simulator/best-games/best-tabletop-simulator-mods"
                )
            )
            list.add(
                BlogPost(
                    "Browsing Tabletop",
                    "Browse the newest, top selling and discounted Tabletop products on Steam",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/STEAM_IMAGE.png",
                    "Steam",
                    "https://store.steampowered.com/tags/en/Tabletop/"
                )
            )
            list.add(
                BlogPost(
                    "The best tabletop RPGs you can buy right now",
                    "Host a campaign or an impromptu one-shot with the best tabletop RPGs around",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/RPGs_IMAGE.png",
                    "Connor Sheridan ",
                    "https://www.gamesradar.com/best-tabletop-rpgs/"
                )
            )
            list.add(
                BlogPost(
                    "The best tabletop games that we played in 2020",
                    "Our favorite board games, role-playing games, and more",
                    "https://raw.githubusercontent.com/huangwei0/ebay_image/master/TABLETOP_20_IMAGE_2.png",
                    "Charlie Hall",
                    "https://www.polygon.com/2020/12/28/22197688/best-board-games-rpgs-tabletop-2020"
                )
            )
            return list
        }
    }
}