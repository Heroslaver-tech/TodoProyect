import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.PetInventoryBinding
import com.example.project.model.ToDo
import com.example.project.view.viewholder.ToDoViewHolder
import com.example.project.viewmodel.ToDoViewModel

class ToDoAdapter(
    private var listToDo: List<ToDo>,
    private val navController: NavController,
    private val viewModel: ToDoViewModel
) : RecyclerView.Adapter<ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = PetInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding, navController) { todo, isChecked ->
            viewModel.updateToDoStatus(todo.titulo, isChecked)
        }
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val toDo = listToDo[position]
        holder.setToDo(toDo)
    }

    override fun getItemCount(): Int {
        return listToDo.size
    }

    // Método para actualizar la lista de tareas
    fun updateList(newList: List<ToDo>) {
        listToDo = newList
        notifyDataSetChanged()
        Log.d(TAG, "Updated ToDo list: $newList") // Añadir este log
    }

    companion object {
        private const val TAG = "ToDoAdapter"
    }
}
