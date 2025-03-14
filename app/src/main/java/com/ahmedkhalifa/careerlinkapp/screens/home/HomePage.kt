package com.ahmedkhalifa.careerlinkapp.screens.home


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.models.Job


@Composable

fun HomePage(name: String, onClick: () -> Unit) {
    HomePageContent()
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePageContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(   modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ){
            item{
                PopularJobsSection()
            }
            item{
                RecentPostsSection()
            }

        }
    }
}

@Composable
fun RecentPostsSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recent Post", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Show All", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        recentPosts.forEach { job ->
            PostCard(job = job)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PopularJobsSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Popular Job", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Show All", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(popularJobs) { job ->
                JobCard(job = job)
            }
        }
    }
}
val popularJobs =listOf(
    Job(
        location = "Toronto, Canada",
        category = "Product Management",
        company_logo = "google_logo.png", // Replace with actual logo resource name or URL
        company_logo_url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        company_name = "Google",
        description = "Lead Product Manager role at Google...",
        id = 1,
        job_type = "Full Time",
        publication_date = "2024-10-26",
        salary = "$2500/m",
        tags = listOf("Product", "Management", "Tech"),
        title = "Lead Product Manager",
        url = "https://careers.google.com/jobs/results/1234567890"
    ),
    Job(
        location = "Toronto, Canada",
        category = "Product Management",
        company_logo = "google_logo.png", // Replace with actual logo resource name or URL
        company_logo_url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        company_name = "Google",
        description = "Lead Product Manager role at Google...",
        id = 2,
        job_type = "Full Time",
        publication_date = "2024-10-26",
        salary = "$2500/m",
        tags = listOf("Product", "Management", "Tech"),
        title = "Lead Product Manager",
        url = "https://careers.google.com/jobs/results/1234567890"
    ),
    Job(
        location = "Toronto, Canada",
        category = "Product Management",
        company_logo = "google_logo.png", // Replace with actual logo resource name or URL
        company_logo_url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        company_name = "Google",
        description = "Lead Product Manager role at Google...",
        id = 3,
        job_type = "Full Time",
        publication_date = "2024-10-26",
        salary = "$2500/m",
        tags = listOf("Product", "Management", "Tech"),
        title = "Lead Product Manager",
        url = "https://careers.google.com/jobs/results/1234567890"
    ),
    Job(
        location = "Toronto, Canada",
        category = "Product Management",
        company_logo = "google_logo.png", // Replace with actual logo resource name or URL
        company_logo_url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        company_name = "Google",
        description = "Lead Product Manager role at Google...",
        id = 4,
        job_type = "Full Time",
        publication_date = "2024-10-26",
        salary = "$2500/m",
        tags = listOf("Product", "Management", "Tech"),
        title = "Lead Product Manager",
        url = "https://careers.google.com/jobs/results/1234567890"
    ),
)
val recentPosts =listOf(
    Job(
        location = "Toronto, Canada",
        category = "Product Management",
        company_logo = "google_logo.png", // Replace with actual logo resource name or URL
        company_logo_url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        company_name = "Google",
        description = "Lead Product Manager role at Google...",
        id = 1,
        job_type = "Full Time",
        publication_date = "2024-10-26",
        salary = "$2500/m",
        tags = listOf("Product", "Management", "Tech"),
        title = "Lead Product Manager",
        url = "https://careers.google.com/jobs/results/1234567890"
    ),
    Job(
        location = "Toronto, Canada",
        category = "Product Management",
        company_logo = "google_logo.png", // Replace with actual logo resource name or URL
        company_logo_url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        company_name = "Google",
        description = "Lead Product Manager role at Google...",
        id = 2,
        job_type = "Full Time",
        publication_date = "2024-10-26",
        salary = "$2500/m",
        tags = listOf("Product", "Management", "Tech"),
        title = "Lead Product Manager",
        url = "https://careers.google.com/jobs/results/1234567890"
    ),
    Job(
        location = "Toronto, Canada",
        category = "Product Management",
        company_logo = "google_logo.png", // Replace with actual logo resource name or URL
        company_logo_url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        company_name = "Google",
        description = "Lead Product Manager role at Google...",
        id = 3,
        job_type = "Full Time",
        publication_date = "2024-10-26",
        salary = "$2500/m",
        tags = listOf("Product", "Management", "Tech"),
        title = "Lead Product Manager",
        url = "https://careers.google.com/jobs/results/1234567890"
    ),
    Job(
        location = "Toronto, Canada",
        category = "Product Management",
        company_logo = "google_logo.png", // Replace with actual logo resource name or URL
        company_logo_url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        company_name = "Google",
        description = "Lead Product Manager role at Google...",
        id = 4,
        job_type = "Full Time",
        publication_date = "2024-10-26",
        salary = "$2500/m",
        tags = listOf("Product", "Management", "Tech"),
        title = "Lead Product Manager",
        url = "https://careers.google.com/jobs/results/1234567890"
    ),
)
@Composable
fun JobCard(job: Job) {
    Card(modifier = Modifier.width(200.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = rememberAsyncImagePainter(model = job.company_logo_url), contentDescription = "")
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.save_ic), contentDescription = "Favorite") // Replace with your heart icon
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(job.title, fontWeight = FontWeight.Bold)
            Text("${job.salary}/m ${job.location}", color = Color.Gray)
        }
    }
}
@Composable
fun PostCard(job: Job) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(painter = rememberAsyncImagePainter(model = job.company_logo_url), contentDescription = "")

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(job.title, fontWeight = FontWeight.Bold)
                Text(job.job_type, color = Color.Gray)
                Text("${job.salary}/m", color = Color.Gray)
            }
        }
    }
}


@Composable
fun SearchBar() {
    OutlinedTextField(value = "", onValueChange = {}, leadingIcon = {
        Icon(Icons.Filled.Search, contentDescription = "Search")
    }, placeholder = { Text(text = "Search Here...") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu"
            ) // Replace with your menu icon
        }

        Image(
            painter = painterResource(id = R.drawable.profile_pic), // Replace with your profile pic
            contentDescription = "Profile",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomePage() {
    HomePage("HOME PAGE") {

    }
}