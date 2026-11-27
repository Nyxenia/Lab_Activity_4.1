package com.example.labactivity41

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var addSavoryButton: Button
    private lateinit var addSweetButton: Button
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.main_recipes_list)
        titleEditText = findViewById(R.id.main_recipe_title)
        descriptionEditText = findViewById(R.id.main_recipe_description)
        addSavoryButton = findViewById(R.id.main_add_savory_button)
        addSweetButton = findViewById(R.id.main_add_sweet_button)

        recipeAdapter = RecipeAdapter { recipe ->
            showRecipeDialog(recipe)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recipeAdapter

        setupSwipeToDelete()

        addSavoryButton.setOnClickListener {
            addRecipe(Flavor.SAVORY)
        }

        addSweetButton.setOnClickListener {
            addRecipe(Flavor.SWEET)
        }
    }

    private fun addRecipe(flavor: Flavor) {
        val title = titleEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()

        if (title.isNotEmpty()) {
            val recipe = Recipe(title, description, flavor)
            recipeAdapter.addRecipe(recipe)
            clearForm()
        }
    }

    private fun clearForm() {
        titleEditText.text.clear()
        descriptionEditText.text.clear()
    }

    private fun showRecipeDialog(recipe: Recipe) {
        AlertDialog.Builder(this)
            .setTitle(recipe.title)
            .setMessage(recipe.description)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val position = viewHolder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION && recipeAdapter.isHeader(position)) {
                    return 0
                }
                return super.getSwipeDirs(recyclerView, viewHolder)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    recipeAdapter.removeRecipeAt(position)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
