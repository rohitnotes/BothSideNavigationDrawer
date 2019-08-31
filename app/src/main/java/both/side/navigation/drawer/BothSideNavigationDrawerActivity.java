package both.side.navigation.drawer;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;
import android.content.res.Configuration;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;

public class BothSideNavigationDrawerActivity extends AppCompatActivity {

    //******* tool bar variable**********
    private Toolbar toolbar;
    public static ActionBar actionbar;

    //********** drawer item using list view ***********************
    private ListView listViewLeft;
    private ListView listViewRight;
    private MenuItemAdapter menuItemAdapter;
    private List<DrawerMenuItem> menuItemValue = new ArrayList<DrawerMenuItem>();

    //******* drawer other variable *******
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    //******* index to identify current navigation menu item *******
    public static int navigationItemIndex = 0;

    //******* drawer header variable *********
    private NavigationView navigationViewLeft;
    private NavigationView navigationViewRight;
    private View navigationDrawerHeaderViewLeft;
    private View navigationDrawerHeaderViewRight;
    public TextView userName,userEmail;
    public CircleImageView userImage;

    //******* floating action button variable *********
    FloatingActionButton floatingActionButton;

    ImageView imageViewLeft,imageViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_both_side_navigation_drawer);

        setupToolbar();         //***** call setupToolbar Function ********
        setupDrawer();          //***** call setupDrawer Function ********
        setupHeader();          //***** call setupHeader Function ********
        setupDrawerMenuList(); //***** call setupDrawerMenuList Function ********

        imageViewLeft=findViewById(R.id.left_menu);
        imageViewRight=findViewById(R.id.right_menu);

        imageViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        imageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END))
                    drawerLayout.closeDrawer(GravityCompat.END);
                else drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        //********* Floating Button *********
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Welcome To First Navigation Drawer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //***** set default fragment ********
        refreshFragment(new HomeFragment());
        setActionBarTitle("Home");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    //************* setup toolbar ****************
    private void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        assert actionbar != null;
    }
    //************* end ****************

    //************* setup drawer ****************
    private void setupDrawer()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView)
            {

            }
            @Override
            public void onDrawerOpened(View drawerView)
            {

            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    //************* end ****************

    //************* setup header ****************
    private void setupHeader()
    {
        navigationViewLeft = (NavigationView) findViewById(R.id.navigation_view_left);
        navigationDrawerHeaderViewLeft = navigationViewLeft.getHeaderView(0);

        userImage    =  navigationDrawerHeaderViewLeft.findViewById(R.id.userProfileImage);
        userName     =  navigationDrawerHeaderViewLeft.findViewById(R.id.userName);
        userEmail    =  navigationDrawerHeaderViewLeft.findViewById(R.id.userEmail);

        navigationDrawerHeaderViewLeft.findViewById(R.id.header).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Header Click Left", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        navigationViewRight = (NavigationView) findViewById(R.id.navigation_view_right);
        navigationDrawerHeaderViewRight = navigationViewRight.getHeaderView(0);

        userImage    =  navigationDrawerHeaderViewRight.findViewById(R.id.userProfileImage);
        userName     =  navigationDrawerHeaderViewRight.findViewById(R.id.userName);
        userEmail    =  navigationDrawerHeaderViewRight.findViewById(R.id.userEmail);

        navigationDrawerHeaderViewRight.findViewById(R.id.header).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Header Click Right", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

    }
    //************* end ****************

    //************* setup drawer menu list ****************
    private void setupDrawerMenuList()
    {
        listViewLeft = (ListView) findViewById(R.id.list_left);
        listViewRight= (ListView) findViewById(R.id.list_right);

        menuItemValue.add(new DrawerMenuItem("Home", "Home location", R.drawable.ic_menu_camera));
        menuItemValue.add(new DrawerMenuItem("Gallery", "Your photos", R.drawable.ic_menu_gallery));
        menuItemValue.add(new DrawerMenuItem("Slideshow", "Your video", R.drawable.ic_menu_slideshow));
        menuItemValue.add(new DrawerMenuItem("Tools", "Tools kit", R.drawable.ic_menu_manage));
        menuItemValue.add(new DrawerMenuItem("Share", "Share information", R.drawable.ic_menu_share));
        menuItemValue.add(new DrawerMenuItem("Send", "Send email", R.drawable.ic_menu_send));

        menuItemAdapter= new MenuItemAdapter(this, menuItemValue);
        listViewLeft.setAdapter(menuItemAdapter);
        listViewRight.setAdapter(menuItemAdapter);
        menuItemAdapter.notifyDataSetChanged();

        listViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                switch(position) {
                    case 0:
                        navigationItemIndex = 0;
                        HomeFragment homeFragment= new HomeFragment();
                        refreshFragment(homeFragment);
                        break;
                    case 1:
                        navigationItemIndex = 1;
                        GalleryFragment galleryFragment= new GalleryFragment();
                        setFragment(galleryFragment);
                        break;
                    case 2:
                        navigationItemIndex = 2;
                        Toast.makeText(getApplicationContext(), " navigationItemIndex = 2 Item Click", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        navigationItemIndex = 3;
                        Toast.makeText(getApplicationContext(), " navigationItemIndex = 3 Item Click", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        navigationItemIndex = 4;
                        Toast.makeText(getApplicationContext(), "navigationItemIndex = 4 Item Click", Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        navigationItemIndex = 5;
                        //Display Share Via dialogue
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType(NavigationDrawerConstants.SHARE_TEXT_TYPE);
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, NavigationDrawerConstants.SHARE_TITLE);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, NavigationDrawerConstants.SHARE_MESSAGE);
                        startActivity(Intent.createChooser(sharingIntent, NavigationDrawerConstants.SHARE_VIA));
                        Toast.makeText(getApplicationContext(), " navigationItemIndex = 5 Item Click", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Wrong Item Click", Toast.LENGTH_LONG).show();
                }
                setActionBarTitle(menuItemValue.get(position).getTitle());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        listViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                switch(position) {
                    case 0:
                        navigationItemIndex = 0;
                        HomeFragment homeFragment= new HomeFragment();
                        refreshFragment(homeFragment);
                        break;
                    case 1:
                        navigationItemIndex = 1;
                        GalleryFragment galleryFragment= new GalleryFragment();
                        setFragment(galleryFragment);
                        break;
                    case 2:
                        navigationItemIndex = 2;
                        Toast.makeText(getApplicationContext(), " navigationItemIndex = 2 Item Click", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        navigationItemIndex = 3;
                        Toast.makeText(getApplicationContext(), " navigationItemIndex = 3 Item Click", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        navigationItemIndex = 4;
                        Toast.makeText(getApplicationContext(), "navigationItemIndex = 4 Item Click", Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        navigationItemIndex = 5;
                        //Display Share Via dialogue
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType(NavigationDrawerConstants.SHARE_TEXT_TYPE);
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, NavigationDrawerConstants.SHARE_TITLE);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, NavigationDrawerConstants.SHARE_MESSAGE);
                        startActivity(Intent.createChooser(sharingIntent, NavigationDrawerConstants.SHARE_VIA));
                        Toast.makeText(getApplicationContext(), " navigationItemIndex = 5 Item Click", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Wrong Item Click", Toast.LENGTH_LONG).show();
                }
                setActionBarTitle(menuItemValue.get(position).getTitle());
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
    }
    //************* end ****************

    //*************** set fragment method ****************
    public void setFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //*************** show or hide the floatingActionButton ****************
        floatingActionButton();
    }
    //****** end ****************

    //*************** refresh fragment method ****************
    public void refreshFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setActionBarTitle("Home");

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }

        //*************** show or hide the floatingActionButton ****************
        floatingActionButton();
    }
    //****** end ****************

    //****** get selected menu ****************
    private void getSelectedNavigationMenu()
    {
        navigationViewLeft.getMenu().getItem(navigationItemIndex).setChecked(true);
    }
    //****** end ****************

    //****** set action bar title ****************
    public void setActionBarTitle(String title)
    {
        actionbar.setTitle(title);
    }
    //****** end ****************

    //****** show or hide the floatingActionButton ****************
    private void floatingActionButton()
    {
        if (navigationItemIndex == 0)
            floatingActionButton.show();
        else
            floatingActionButton.hide();
    }
    //****** end ****************

    //*************** if all fragment is finish ****************
    @Override
    public void onBackPressed() {

        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (this.drawerLayout.isDrawerOpen(GravityCompat.END))
        {
            this.drawerLayout.closeDrawer(GravityCompat.END);
        }
        else if(fragments == 1)
        {
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.exit_alert_dialog, viewGroup, false);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            Button no = dialogView.findViewById(R.id.no_button);
            Button yes =dialogView.findViewById(R.id.yes_button);

            no.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    alertDialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    alertDialog.dismiss();
                    BothSideNavigationDrawerActivity.this.finish();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
    //****** end ****************
}
