 /**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 */
package com.example.android.justjava


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


 /**
 * This app displays an order form to order coffee.
 */
class MainActivity : AppCompatActivity() {

    var quantity = 2;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    /**
     * This method is called when the plus button is clicked.
     */
    fun increment(view: View?) {
        if (quantity ==100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show()
            return
        }
        quantity += 1
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    fun decrement(view: View?) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show()
            return
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    fun submitOrder(view: View) {
        val nameField = findViewById<View>(R.id.name_field) as EditText
        val name = nameField.text.toString()

        val whippedCreamCheckbox = findViewById<View>(R.id.whipped_cream_checkbox) as CheckBox
        var hasWhippedCream = whippedCreamCheckbox.isChecked()

        val chocoateCheckbox = findViewById<View>(R.id.chocolate_checkbox) as CheckBox
        var hasChocolate = chocoateCheckbox.isChecked()

        var price = calculatePrice(hasWhippedCream, hasChocolate)
        var priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate)

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name)
            putExtra(Intent.EXTRA_TEXT, priceMessage)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }

    }

     /**
      * Calculates the price of the order.
      *
      * @return total price
      */
     fun calculatePrice(hasWhippedCream: Boolean, hasChocolate: Boolean): Int {
         var basePrice = 5;
         if (hasWhippedCream) {
             basePrice += 1
         }
         if (hasChocolate) {
             basePrice += 2
         }
         return quantity * basePrice
     }

     /**
      * Calculates the order summary
      */
     fun createOrderSummary(name: String, int: Int, hasWhippedCream: Boolean, hasChocolate: Boolean): String {
         var priceMessage = "Name: " + name
         priceMessage += "\nAdd whipped cream? " + hasWhippedCream
         priceMessage += "\nAdd chocolate? " + hasChocolate
         priceMessage += "\nQuantity: " + quantity
         priceMessage += "\nTotal: $" + calculatePrice(hasWhippedCream, hasChocolate)
         priceMessage += "\n" + getString(R.string.thank_you)
         return priceMessage
     }

    /**
     * This method displays the given quantity value on the screen.
     */
    private fun displayQuantity(number: Int) {
        val quantityTextView = findViewById<View>(R.id.quantity_text_view) as TextView
        quantityTextView.text = "" + number
    }

}