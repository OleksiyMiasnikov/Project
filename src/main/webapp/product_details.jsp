<!DOCTYPE html>
<html>
    <body>
        <%@ include file="header.jspf" %>
        <script src=></script>
            <div class="dataBox">

String title = "*** New product ***";

if (strId != null)  title = "*** Update product ***";

<span style=\"font-size: 8px;\">" +
"[product_details.jsp]" +
"</span>"
<p style=\"text-align: center; font-size: 22px;font-weight: bold\">" +
title +
"</p>"
<hr>"
if (strId != null) {
<span class=\"header_key\">" +
"Id" +
"</span>"
<input class=\"header_value\" name=\"newId\" value=\"" +
product.getId() +
"\">"
<br>"
}
<span class=\"header_key\">" +
"Product name" +
"</span>"
<input class=\"header_value\" name=\"newName\" value=\"" +
product.getName() +
"\">"
<br>"
<span class=\"header_key\">" +
"Unit" +
"</span>"

<select class=\"header_value\" name=\"newUnit\" value=\"" +
product.getUnit().getLabelUa() +
"\">"
<option> +
product.getUnit().getLabelUa() +
</option>
for (Unit unit: Unit.values()) {
<option value="" +
unit.labelUa +
>
unit.labelUa
</option>
}
</select>
<br>
<span class="header_key">
    Price
</span>
<input class="header_value" name="newPrice" value=${result.price} pattern="^[0-9]+\\.?[0-9]{0,2}$">
<br>
<hr>
<p>
    <button class="submit_button" type="submit" name="command" value="Create product">
        Ok
    </button>
    <button class="submit_button" type="submit" name="command" value="List of products">
        Cancel
    </button>
</p>

            </div>
        </form>
    </body>
</html>